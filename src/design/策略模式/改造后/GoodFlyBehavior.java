package design.策略模式.改造后;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-09-17 10:41
 */
public class GoodFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("good fly");
    }
}
