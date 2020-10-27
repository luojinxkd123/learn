package java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

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

    }
}
