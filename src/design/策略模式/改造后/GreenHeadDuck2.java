package design.策略模式.改造后;
/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-09-17 10:43
 */
public class GreenHeadDuck2 extends Duck2 {


    public GreenHeadDuck2() {
        flyBehavior= new GoodFlyBehavior();
        quackBehavior= new GagaQuackBehavior();//这里是给超类中对象赋值
    }

    @Override
    public void display() {
        System.out.println("哈哈哈");
    }
}
