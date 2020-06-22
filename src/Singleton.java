/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-06-22 09:47
 */
public class Singleton {
    //写一个高并发的单例模式，此处使用懒汉模式，双锁机制，volatile保证线程安全
    volatile private static Singleton singleton = null;
    //双锁机制其实已经可以基本保证单例，但是才高并发下，虚拟机优化指令重排，第一个线程没有完成初始化
    // ，第二个线程读取到的singleton依旧为null，进行了第二次实例化
    // new Instance();的步骤
    //memory = allocate();　　// 1：分配对象的内存空间
    //ctorInstance(memory);　// 2：初始化对象
    //instance = memory;　　// 3：设置instance指向刚分配的内存地址
    //根源在于代码中的2和3之间，可能会被重排序。例如：

    //memory = allocate();　　// 1：分配对象的内存空间
    //instance = memory;　　// 3：设置instance指向刚分配的内存地址
    // 注意，此时对象还没有被初始化！
   // ctorInstance(memory);　// 2：初始化对象

    //没有初始化的内存为null，所以要加volatile
    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                    System.out.println("实例化Singleton");
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> Singleton.getInstance()).start();
        }
    }
}
