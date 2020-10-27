package design.策略模式.改造前;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-09-17 10:14
 */
public abstract class Duck {
    /**
     * 叫
     */
    public void quack() {
        System.out.println("gaga");
    }
    public abstract void display();

    /**
     * 游泳
     */
    public void swim() {
        System.out.println("im swim");
    }
}
