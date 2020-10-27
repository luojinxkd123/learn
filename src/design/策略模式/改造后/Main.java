package design.策略模式.改造后;


/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-09-17 10:22
 */
public class Main {
    public static void main(String[] args) {
        Duck2 greenHeadDuck = new GreenHeadDuck2();

        greenHeadDuck.quack();
        greenHeadDuck.fly();
        greenHeadDuck.display();
    }
}
