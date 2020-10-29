import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-10-28 17:42
 */
public class CollectionNote {
    /**
     * java集合框架大致分成List、Set、Queue、Map(Map不继承Collection)四种接口体系
     * List(有序可重复)
     * Set(无序不可重复)
     * Queue(队列集合)
     * Map(k-v集合)
     * Collection接口的方法add、addAll、clear、contains、containsAll、equals、
     * hashCode、isEmpty、iterator、remove、removeAll、retailAll、size、toArray
     * Map接口的方法size、isEmpty、containsKey、containsValue、get、set、remove、
     * putAll、clear、keySet、values、entrySet、equals、hashCode
     */


    /**
     * 集合       集合类             有序性      线程安全   重复存在     底层实现
     * Map       HashMap            否        否         是         数组+链表|红黑树(长度大于8时)[jdk1.8以前，采用分段锁机制]
     * Map       HashTable          否        是         是         对方法加锁保证线程安全
     * Map       LinkedHashMap      是        否         是         双向链表维护次序，查找快，增删慢
     * Map       Properties         否        是         是         HashTable的子类。主要用于读取配置文件
     * Map       TreeMap            是        否         是         SortedMap的实现，数据结构为红黑树
     *                                                             构造方法传入Comparator接口则是定制排序，否则是自然排序
     *
     *
     * Set       HashSet            否        否         否         构造方法是构造一个HashMap
     * Set       LinkedHashSet      是        否         否         HashSet的字类，通过链表维护次序
     * Set       TreeSet            是        否         否         构造方法是构造一个TreeMap，通过红黑树存储数据，链表存储顺序
     * Set       EnumSet            是        否         否         不允许添加null，为枚举而设定的
     *
     *
     * List      ArrayList          是        否         是         基于动态数组，数组内存连续，所以读取寻址比较容易，增删比较困难
     *                                                             (修改数组会导致内存重新分配，如需高效读取就建议使用数组)
     * List      LinkedList         是        否         是         基于链表，读取需要移动指针，所以读取寻址比较困难，增删比较容易
     *                                                             (增删开销统一，如需频繁操作对象就建议使用链表)
     * List      Vector             是        是         是         和ArrayList差不多，通过加锁保证线程安全
     * List      Stack              是        是         是         继承自 Vector
     *
     * Set是单独，Hash是散列无序，Tree-Link-List是有序， HashTable、Vector是安全，
     */
    /**
     *  JDK1.7====>JDK1.8摈弃Segment分段锁，采用CAS Compare And Swap比较并交换、
     *  CAS的优势：非阻塞，自旋获取锁，不会让线程挂起
     */
}
