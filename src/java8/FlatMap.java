package java8;

import java.util.Arrays;
import java.util.List;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2021-03-22 16:50
 */
public class FlatMap {
    public static void main(String[] args) {
        /**
         * 合并多个stream
         */
        List<String> list = Arrays.asList("hello world","hello world hello ","hello hello world");
        list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).distinct().forEach(System.out::println);
    }
}
