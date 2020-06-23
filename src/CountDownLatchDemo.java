import java.util.concurrent.*;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-23 17:29
 */
public class CountDownLatchDemo {
    //等待数量变为0，主线程继续执行
    public static void main(String[] args) {
        CountDownLatch count = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName());
                count.countDown();
            }).start();
        }
        try {
            count.await();//保证main在最后执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main");
    }
    //这个和CountDownLatch正好相反，做加法的
    static class CyclicBarrierDemo{
        public static void main(String[] args) {
            CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("结束"));
            for (int i = 0; i < 7; i++) {
                new Thread(()->{
                    try {
                        System.out.println(Thread.currentThread().getName());
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }
    //信号标  //不同于上面的是这个是可以不断复用
    static class SemaphoreDemo {
        public static void main(String[] args) {
            Semaphore semaphore = new Semaphore(3);//3个车位
            for (int i = 0; i < 100; i++) {//一百个来抢车位
                new Thread(()->{
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"\t抢到车位");
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName()+"\t停车1秒离开车位");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }

                }).start();
            }

        }

    }
}
