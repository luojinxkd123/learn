import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            Set<Integer> set = new TreeSet<>();
            int n= scanner.nextInt();
            set.clear();
            if (n > 0) {
                for (int i = 0; i < n; i++) {
                    set.add(scanner.nextInt());
                }
            }
            set.stream().forEach(System.out::println);
        }
    }

}