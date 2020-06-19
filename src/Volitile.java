import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Volitile {    //java虚拟机和java内存模型

    //知识点：volatile的三大性质
    //1、可见性
    //2、不保证原子性
    //3、禁止指令重排


    //1、可见性
    static class Volitile01{
        volatile int num = 0;//如果不加volatile则跳不出while循环，因为num需要对所有线程共享可见，一旦改变则需要立刻通知其他线程

        public void addNum() {
            num = num + 60;
        }

        public static void main(String[] args) {
            Volitile01 volititle01 = new Volitile01();
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                volititle01.addNum();
                System.out.println(Thread.currentThread().getName()+"执行");
            },"AAA").start();
            while (volititle01.num == 0) {

            }
            System.out.println(Thread.currentThread().getName()+"跳出while");
        }
    }
    //2、不保证原子性
    static class Volitile02{
        volatile int num = 0;
        public void addNum() {
            num++;
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        public void addAtomic() {
            atomicInteger.getAndAdd(1);
        }

        public static void main(String[] args) {
            Volitile02 volitile02 = new Volitile02();
            for (int i = 1; i <= 20; i++) {
                new Thread(() -> {
                    for (int j = 1; j <= 10000; j++) {
                        volitile02.addNum(); //volitle线程不安全
                        volitile02.addAtomic();//用atomic来代替，这样子即可不加lock也能高效的保证数据安全
                    }
                }).start();
            }
            while (Thread.activeCount() > 2) {
                Thread.yield();
            }
            System.out.println("num:" + volitile02.num);
            System.out.println("atomicInteger:" + volitile02.atomicInteger);
        }
    }

    //3、禁止指令重排
    static class Volitile03 {
        int a = 0;
        boolean flag = false;
        public void method01() throws InterruptedException {
            a = 1;
            flag = true;
        }
        public void method02() {
            if (flag) {
                a = a + 5;
            }
        }

        public static void main(String[] args) throws Exception {
            for (int i = 0; i < 100; i++) {
                Volitile03 volitile03 = new Volitile03();
                Thread thread01 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            volitile03.method01();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Thread thread02 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        volitile03.method02();
                    }
                });
                thread01.start();thread02.start();
                thread01.join();thread02.join();//等待两个线程执行完毕后输出//区别于yield
                System.out.println(volitile03.a);
            }

        }
    }
}
//对线程的一个总结
//method:       sleep  wait  yield  join
//releaseLock:  false  true  false  false
//blocking:     true   true  false  true
//condition==>>>
//sleep:不考虑线程的优先级
//wait:notify才能唤醒。没有在synchronized修饰的代码块中使用时运行时会抛出IllegalMonitorStateException的异常
//yield:执行yield()的线程有可能在进入到可执行状态后马上又被执行,yield 方法只能使同优先级或更高优先级的线程有执行的机会(不阻塞，重新进入就绪)
//join:等待异步线程执行完结果之后才能继续运行的场景
//  wait notify notifyAll 必须在synchronized中使用