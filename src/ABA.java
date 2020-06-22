import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-22 15:27
 */
public class ABA {
    //CAS 可能产生ABA问题。A线程把数据从1变为2又变为1，B线程进入发现数据为1，以为没有被操作过，发生错误操作

    //AtomicReference
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    //原子引用解决ABA
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
    public static void main(String[] args) {
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
            atomicStampedReference.compareAndSet(100, 101, 1, 2);
            atomicStampedReference.compareAndSet(101, 100, 2, 3);
        },"t1").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2020) + "\t没有版本号的" + atomicReference.get());
            System.out.println(
                    atomicStampedReference.compareAndSet(100, 2020, 1, 2)
                    +"\t有版本号的"+atomicStampedReference.getReference()+"当前版本："+atomicStampedReference.getStamp());

        },"t2").start();
    }

}
