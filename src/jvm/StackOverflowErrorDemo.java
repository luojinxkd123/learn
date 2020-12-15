package jvm;

import java.util.ArrayList;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-11-26 10:53
 */
public class StackOverflowErrorDemo {
    public static void main(String args[]) {
        a();
    }
    public static void a() {
        int x = 0;
        b();
    }
    public static void b() {
        //ArrayList<Object> objects = new ArrayList<>(10000000);
        c();
    }
    public static void c() {
        float z = 0f;
        a();
    }
    /**
     * 无限递归循环调用（最常见）。
     *
     * 执行了大量方法，导致线程栈空间耗尽。
     *
     * 方法内声明了海量的局部变量。
     *
     * native 代码有栈上分配的逻辑，并且要求的内存还不小，比如 java.net.SocketInputStream.read0 会在栈上要求分配一个 64KB 的缓存（64位 Linux）。
     */
}
