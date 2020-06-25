import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-23 10:54
 */
public class LockDemo {
    //锁的介绍：
    /*
    1、公平、非公平锁
    公平锁：是指多个线程按照申请锁的顺序来获取锁。ReentrantLock(true)

    非公平锁：是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁
    在高并发情况下有可能造成优先级反转或者饥饿现象ReentrantLock(false)默认
    非公平的吞吐量比公平锁大。
    对于synchronized而言，也是一种非公平锁

    2、可重入锁（又名递归锁）
    指的是统一线程外层函数获得锁之后，内存递归函数仍然能获取该锁的代码
    在统一线程在外城方法获取锁的时候，在进入内层方法会自动获取锁
    也即是说，线程可以进入任何一个它已经拥有的锁，所同步着的代码块
    ReentrantLock和Synchronized就是典型的可重入锁
    作用：避免死锁

    3、自旋锁SpinLock（非阻塞，循环探查）
    是指尝试获取锁的线程不会立即阻塞，而是才用循环的方式取尝试获取锁，这样的好处是减少线程上下文切换的消耗
    缺点是循环会消耗cpu

    4、独占锁：指该锁一次只能被一个线程所持有，ReentrantLock和Synchronized都是独占锁
    共享锁：指该锁可以被多个线程所持有
    对ReentrantReadWriteLock其读锁是共享锁，写锁是独占锁
    读锁的共享锁可以保证并发读非常高效的，读写，写读，写写的过程是互斥的。
    */

    //锁的细粒度和灵活度：很明显ReenTrantLock优于Synchronized，官方推荐使用Synchronized


    //锁的升级
    //synchronized wait notify  JVM层面  不可中断  非公平锁                 底层monitor
    //lock await                API层面  可中断    精确唤醒
    /**
     * 2、可重入锁（又名递归锁）
     *     指的是统一线程外层函数获得锁之后，内存递归函数仍然能获取该锁的代码
     *     在统一线程在外城方法获取锁的时候，在进入内层方法会自动获取锁
     *     也即是说，线程可以进入任何一个它已经拥有的锁，所同步着的代码块
     *     ReentrantLock和Synchronized就是典型的可重入锁
     *     作用：避免死锁
     */
    static class ReentrantLockDemo{
        ReentrantLock lock = new ReentrantLock();//非公平锁
        public static void main(String[] args) {
            ReentrantLockDemo lock = new ReentrantLockDemo();
            new Thread(() -> {
                lock.get();
            }, "t1").start();
            new Thread(() -> {
                lock.get();
            }, "t2").start();
        }
        public void get() {
            lock.lock();
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t get()");
                set();
            }finally {
                lock.unlock();
                lock.unlock();//加几个锁就解锁几次
            }
        }
        public void set() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t set()");
            }finally {
                lock.unlock();
            }
        }

    }

    /**
     * 3、自旋锁SpinLock（非阻塞，循环探查）
     * 是指尝试获取锁的线程不会立即阻塞，而是才用循环的方式取尝试获取锁，这样的好处是减少线程上下文切换的消耗
     * 缺点是循环会消耗cpu
     */
    static class SpinLockDemo {
        //手写一个自旋锁
        AtomicReference<Thread> atomicReference = new AtomicReference<>();
        //lock
        public void myLock() {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName()+"\t come in Lock");
            while (!atomicReference.compareAndSet(null, thread)) {
                //System.out.println(thread.getName()+"\t is in wait unlock");
            }
        }
        public void myUnLock() {
            Thread thread = Thread.currentThread();
            System.out.println(thread.getName()+"\t is unLock");
            atomicReference.compareAndSet(thread, null);
        }

        public static void main(String[] args) {
            SpinLockDemo demo = new SpinLockDemo();
            new Thread(() -> {
                demo.myLock();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                demo.myUnLock();
            }, "Thread-A").start();

            //main线程休眠一秒以保证A线程先执行
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(() -> {
                demo.myLock();
                demo.myUnLock();
            }, "Thread-B").start();
        }
    }

    /**
     * 对ReentrantReadWriteLock其读锁是共享锁，写锁是独占锁
     *     读锁的共享锁可以保证并发读非常高效的，读写，写读，写写的过程是互斥的。
     */
    //读写高效，高可靠性读写分离锁
    static class ReentrantReadWriteLockDemo {
        volatile Map<String, Object> map = new HashMap<>();
        static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        public void put(String key, Object value) {
            try {
                rwLock.writeLock().lock();
                System.out.println(Thread.currentThread().getName() + "\t正在写入" + key);
                map.put(key, value);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t写入完成"+key);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rwLock.writeLock().unlock();
            }

        }
        public void get(String key) {
            try {
                rwLock.readLock().lock();
                System.out.println(Thread.currentThread().getName() + "\t正在读取" + key);
                map.get(key);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t读取完成"+key);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rwLock.readLock().unlock();
            }
        }

        public static void main(String[] args) {
            ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();
            for (int i = 1; i <= 5; i++) {
                final int  tempInt=i;
                new Thread(() -> {
                    demo.put(tempInt + "", tempInt);
                }, "Thread-" + i).start();
            }
            for (int i = 5; i <= 10; i++) {
                final int  tempInt=i;
                new Thread(() -> {
                    demo.get(tempInt + "");
                }, "Thread-" + i).start();
            }
        }
    }

    //用conditon精确等待唤醒
    //锁的升级
    //synchronized wait notify  JVM层面  不可中断  非公平锁                 底层monitor
    //lock await                API层面  可中断    精确唤醒
    static class ReentrantAndSynchronizedLock {
        //更加细腻的使用，且可中断
        private Lock lock = new ReentrantLock();
        private Condition c1 = lock.newCondition();
        private Condition c2 = lock.newCondition();
        private Condition c3 = lock.newCondition();
        private int num = 1;
        public void print5() {
            try {
                lock.lock();
                //1、判断
                while (num != 1) {
                    c1.await();//等待
                }
                //2、干活
                for (int i = 0; i < 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
                }
                //3、通知
                num = 2;
                c2.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public void print10() {
            try {
                lock.lock();
                //1、判断
                while (num != 2) {
                    c2.await();//等待
                }
                //2、干活
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
                }
                //3、通知
                num = 3;
                c3.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public void print15() {
            try {
                lock.lock();
                //1、判断
                while (num != 3) {
                    c3.await();//等待
                }
                //2、干活
                for (int i = 0; i < 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
                }
                //3、通知
                num = 1;
                c1.signal();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        public static void main(String[] args) {
            ReentrantAndSynchronizedLock demo = new ReentrantAndSynchronizedLock();
            new Thread(()->{
                for (int i = 0; i <10 ; i++) {
                    demo.print5();
                }
            },"Thread-A").start();
            new Thread(()->{
                for (int i = 0; i <10 ; i++) {
                    demo.print10();
                }
            },"Thread-B").start();
            new Thread(()->{
                for (int i = 0; i <10 ; i++) {
                    demo.print15();
                }
            },"Thread-C").start();
        }
    }
}
