import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-22 15:47
 */
public class CollectionNotSafe {
    //集合不安全问题
    //ConcurrentModificationException
    /**
     * 1、故障现象java.util.ConcurrentModificationException
     * 2、导致原因
     *      并发争抢修改导致，参考我们的花名册签名情况
     *      一个人正在写入，另一个同学过来抢夺。
     * 3、解决方法
     *      3.1、new Vector<>();
     *      3.2、Collections.synchronizedList(new ArrayList<>());
     *      3.3、new CopyOnWriteArrayList();--写时复制，读写分离的思想
     * 4、优化的建议
     *
     */
    //3.3  CopyOnWriteArrayList的add
    /*public boolean add(E e) {
        final ReentrantLock . = this.lock; //线程加锁其余线程不可读
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);  //线程读，复制后去操作
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock(); //解锁后其他线程可读取
        }
    }*/
    public static void main(String[] args) {
        //List<String> list = new ArrayList<>();
        //List<String> list = Collections.synchronizedList(new ArrayList<>());//加锁
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }).start();
        }
    }
}
