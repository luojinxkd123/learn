package design.策略模式.改造后;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-09-17 10:42
 */
public class GagaQuackBehavior implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("gaga");
    }
}
