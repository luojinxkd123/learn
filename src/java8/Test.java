package java8;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-10-14 17:21
 */
@FunctionalInterface
interface TestFunctionalInterface {
    void test();

    boolean equals(Object obj);

}

public class Test<T> {
    public T myTest(TestFunctionalInterface testFunctionalInterface) {
        testFunctionalInterface.test();
        return null;
    }
    public static void main(String[] args) {

        List<Optional<String>> objects = new ArrayList<>();
        objects.add(Optional.of("22"));
        objects.add(Optional.empty());
        objects.stream().forEach(x -> x.ifPresent(System.out::print));
        objects.stream().filter(Objects::isNull);
        objects.sort(new Comparator<Optional<String>>() {
            @Override
            public int compare(Optional<String> o1, Optional<String> o2) {
                return 0;
            }
        });
        objects.stream().distinct().collect(ArrayList::new, List::add, (list1, list2) -> list1.addAll(list2));
        objects.stream().collect(Collectors.toList());
        //objects.stream().collect(Collectors.joining());

        List<String> list = Arrays.asList("hello world","hello world hello ","hello hello world");
        list.stream().map(item -> item.split(" ")).flatMap(Arrays::stream).forEach(System.out::println);
    }
}
