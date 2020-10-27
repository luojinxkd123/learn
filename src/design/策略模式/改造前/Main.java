package design.策略模式.改造前;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-09-17 10:22
 */
public class Main {
    public static void main(String[] args) {
        GreenHeadDuck greenHeadDuck = new GreenHeadDuck();
        RedHeadDuck redHeadDuck = new RedHeadDuck();

        greenHeadDuck.display();
        greenHeadDuck.quack();
        greenHeadDuck.swim();

        redHeadDuck.display();
        redHeadDuck.quack();
        redHeadDuck.swim();
    }
}
