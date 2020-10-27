package design.策略模式.改造后;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-09-17 10:34
 */
public abstract class Duck2 {
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;
    public Duck2() {

    }
    public void fly() {
        flyBehavior.fly();
    }
    public void quack() {
        quackBehavior.quack();
    }

    public abstract void display();
}
