package java8;

import java.awt.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-11-12 17:03
 */
public class sss {
    public static void main(String[] args) {
        ExecutorService threadPool = null;
        try {
            threadPool = Executors.newFixedThreadPool(4);
            Future<CopyOnWriteArrayList> future = threadPool.submit(() -> test());
            CopyOnWriteArrayList list = future.get();
            System.out.println(list.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdownNow() ;
        }

    }
    private static CopyOnWriteArrayList test() {
        //IntStream.range(1,10000).collect()
        CopyOnWriteArrayList<Object> objects = new CopyOnWriteArrayList<>();
        Thread thread = new Thread();
        thread.run();
        return objects;
    }
}
