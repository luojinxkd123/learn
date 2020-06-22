import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-22 10:06
 */
//CAS就是比较并且交换 compareAndSwap 依靠的是rt.jar包下的UnSafe类的native方法保证原子性。非常底层，直接操作内存，不可被打断
public class CAS {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.getAndAdd(1);

    }
    /*public final int getAndAddInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
        //自旋do while
        return var5;
    }*/

    //缺点:如果cas失败，会一直在自旋中，造成很大开销


}
