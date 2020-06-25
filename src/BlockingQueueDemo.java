import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-24 09:20
 */
public class BlockingQueueDemo {
    /*
    阻塞队列：

    方法类型    抛出异常    特殊值     阻塞      超时
    ————————————————————————————————————-————————
    插入        add(e)    offer(e)  put(e)    offer(e,time,unit)
    移除        remove()  poll()    take()    poll(time,unit)
    检查        element() peek()    没有       没有

    ArrayBlockingQueue:数组结构 有界阻塞队列
    LinkedBlockingQueue:链表结构 有界阻塞队列 默认大小 Integer.MAX_VALUE（其实这个大小非常大，相当无界了）
    SynchronousQueue:不存储元素的阻塞队列，也即单个元素的队列。
    这三个是重点


    */

    static BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);//阻塞队列长度

    public static void main(String[] args) {
        new Thread(()->{
            try {
                queue.offer("1",1,TimeUnit.SECONDS );
                queue.offer("1",1,TimeUnit.SECONDS );
                queue.offer("1",1,TimeUnit.SECONDS );
                queue.offer("1",1,TimeUnit.SECONDS );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"A").start();
        new Thread(()->{
            System.out.println(queue.peek());
        },"B").start();
    }
}
