package jvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-11-26 11:41
 */
public class OutOfMemoryErrorDemo {
    /**
     * 1、Java Heap 内存溢出    不断new
     * 2、Java Stack内存溢出    过多线程申请栈内存，导致栈内存不足
     * 3、方法区和运行时常量溢出   不断往一个对象里加东西
     */

    public static void main(String[] args) {
        new OutOfMemoryErrorDemo().doIt();
    }

    public void doIt(){
        List<String> objects = new ArrayList<>();

        int i = 0;
        while (true) {
            objects.add((i*i++)+String.valueOf(i++).intern());
        }
    }


}
