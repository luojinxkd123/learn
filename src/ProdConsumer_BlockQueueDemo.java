import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-24 11:31
 */
public class ProdConsumer_BlockQueueDemo {
    //生产者消费者阻塞队列
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static BlockingQueue blockingQueue = null;
    private static volatile boolean FLAG = true;

    public void prod() throws Exception{
        String data;
        boolean retValue;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            retValue = blockingQueue.offer(data, 1L, TimeUnit.SECONDS);
            if (retValue) {
                System.out.println(Thread.currentThread().getName() + "\t生产入队列");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t生产失败");
            }

        }

    }
    public void consum() throws Exception{
        boolean retValue;
        while (FLAG) {
            Object poll = blockingQueue.poll(1L, TimeUnit.SECONDS);
            if (null==poll) {
                System.out.println(Thread.currentThread().getName() + "\t队列无可消费");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t队列消费成功"+poll.toString());
            }

        }
    }

    public ProdConsumer_BlockQueueDemo(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public static void main(String[] args){
        ProdConsumer_BlockQueueDemo demo = new ProdConsumer_BlockQueueDemo(new ArrayBlockingQueue<>(3));
        new Thread(()->{
            try {
                demo.prod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"AAA").start();
        new Thread(()->{
            try {
                demo.consum();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"BBB").start();
    }

}
