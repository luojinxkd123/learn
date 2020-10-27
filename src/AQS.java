import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-10-26 16:00
 */
public class AQS {
    /**
     * 让线程等待和唤醒线程的三种方法
     * 方式1、使用Object中的wait()方法让线程等待，使用Object中的notify()方法唤醒（缺点：无法脱离synchronized使用）
     * 方式2、使用JUC包下的Condition的await()方法让线程等待，使用signal()方法唤醒线程（缺点：无法脱离lock使用）
     * 方式3、LockSupport类可以阻塞当前线程以记唤醒指定被阻塞的线程(优点：1、用法简单，2、调用顺序不受限，不会阻塞死锁)
     */
    public static void main(String[] args) {
        //object();
        //condition();
        lockSupport();
    }
    static Object objectLock = new Object();
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static void object() {
        new Thread(()->{

            synchronized (objectLock) {
                try {
                    System.out.println("等待");
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("已唤醒");
        },"A").start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println("唤醒");
            }
        },"B").start();
    }
    public static void condition() {
        new Thread(()->{
            try {
                System.out.println("等待");
                lock.lock();
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            System.out.println("已唤醒");
        },"A").start();

        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                lock.lock();
                condition.signal();
            }finally {
                lock.unlock();
            }

            System.out.println("唤醒");
        },"B").start();
    }
    public static void lockSupport() {
        Thread a = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("come in");
            LockSupport.park();//被阻塞...等待许可证
            System.out.println("被唤醒");
        }, "A");
        a.start();
        new Thread(() -> {
            System.out.println("唤醒");
            LockSupport.unpark(a);//可以在part前执行，若unpark先执行，park则继续正常执行不会阻塞
            LockSupport.unpark(a);//连续调用两次也只能最多是1个凭证，所以后面只能调用一次park，如果两次调用park则会阻塞
        }, "B").start();
        //unpark是发放许可证，park是消耗许可证，最可证的最大值为1，最小值为0
    }
    /**
     * AQS:抽象的队列同步器:（是JUC体系的基石，
     * 通过内置的FIFO队列来完成资源获取线程的排队工作，并通过一个int类型变量表示持有锁的状态
     * 一个Node节点来实现锁的分配，通过CAS完成对State值得修改）
     */
}
