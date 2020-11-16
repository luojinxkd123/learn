package java8;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-11-09 16:16
 */
public interface Hello {
    default void sort(Comparator<Optional<String>> comparator) {

    }


}
