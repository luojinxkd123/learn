import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.*;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-24 13:56
 */
public class ThreadPool {
    /*
      1、线程池的三个常用方式
      Executors.newFixedThreadPool(int);
      Executors.newSingleThreadExecutor();
      Executors.newCachedThreadPool();

      2、线程池的底层ThreadPoolExecutor=====>>>>>ThreadPoolExecutor的底层BlockQueue

      3、线程池的重要参数介绍：
      public ThreadPoolExecutor(int corePoolSize,//池中常驻核心线程数
                              int maximumPoolSize,//池中能够容纳最大线程数，必须>=1
                              long keepAliveTime,//多余线程的存活时间,当corePoolSize < currentSize < maximumPoolSize
                              TimeUnit unit,//keepAliveTime的单位
                              BlockingQueue<Runnable> workQueue,//任务的阻塞队列，已提交却未被执行的任务
                              ThreadFactory threadFactory,//线程工厂，用于创建线程使用默认的就OK
                              RejectedExecutionHandler handler//拒绝策略
                              )
      4、线程池的工作原理：
        1）、在创建线程池后，等待提交过来的请求任务
        2）、当调用execute()方法添加任务时，线程池会做如下判断：
            2.1：currentSize<corePoolSize，马上调用核心线程处理
            2.2：currentSize>=corePoolSize，任务进入队列
            2.3：队列满了，currentSize<maximumPoolSize，创建非核心线程，处理任务
            2.4：队列满了，currentSize>=maximumPoolSize,会启动饱和君爵策略来执行
        3）、当一个线程完成任务时，会从队列取下一个任务来执行
        4）、当一个线程空闲超过一定时间keepAliveTime时，线程池会做如下判断：if currentSize>corePoolSize，线程销毁
      5、四种拒绝策略 RejectedExecutionHandler
        5.1：AbortPolicy             直接抛出异常 RejectedExecutionHandler 阻止系统正常运行
        5.2：CallerRunsPolicy        "调用者运行"一种调节机制，该策略既不会抛弃任务，也不会抛出异常
        5.3：DiscardOldestPolicy     抛弃队列中等待最久的任务
        5.4：DiscardPolicy           直接丢弃任务
      6、线程池配置：
        6.1：cpu密集型           资源浪费在等待       参考公式：（尽可能少）CPU核数+1
        6.2：还是IO密集型         资源浪费在阻塞       参考公式：（尽可能多）CPU核数/1-阻塞系数 阻塞系数在0.8-0.9
    */
    static class ExecutorDemo {
        //private static ExecutorService threadPool = Executors.newFixedThreadPool(5);//一池5线程
        //private static ExecutorService threadPool = Executors.newSingleThreadExecutor();//一池1线程
        private static ExecutorService threadPool = Executors.newCachedThreadPool();//一池n线程
        public static void main(String[] args) {
            try {
                for (int i = 0; i < 100000; i++) {
                    threadPool.execute(()->System.out.println(Thread.currentThread().getName()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                    threadPool.shutdown();
            }
        }
    }

    static class RejectedExecutionHandlerDemo {
        private static ExecutorService executor = new ThreadPoolExecutor(2,
                5,
                100L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(10000),
                Executors.defaultThreadFactory(),
                //new ThreadPoolExecutor.AbortPolicy());
                new ThreadPoolExecutor.CallerRunsPolicy());
                //new ThreadPoolExecutor.DiscardOldestPolicy());
                //new ThreadPoolExecutor.DiscardPolicy());

        public static void main(String[] args) {
            new Thread(()->{
                try {
                    for (int i = 0; i < 10001; i++) {
                        final int tempInt = i;
                        executor.execute(() -> {
                            System.out.println(Thread.currentThread().getName() + "\t" + tempInt);
                            try {
                                TimeUnit.MILLISECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    executor.shutdown();
                }
            },"回归处理线程").start();
            System.out.println("main线程结束");
        }
    }


    //FutureTask实现Runable接口，可放入Callable接口。可以作为桥梁
    static class MyThread implements Callable<Integer> {
        @Override
        public Integer call() {
            return 1024;
        }

        public static void main(String[] args) throws ExecutionException, InterruptedException {
            FutureTask<Integer> task = new FutureTask<>(new MyThread());

            new Thread(task, "AA").start();
            System.out.println(task.get());

        }
    }
}
