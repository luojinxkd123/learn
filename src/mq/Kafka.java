package mq;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-11-16 14:22
 */
public class Kafka {
    //zk端口2181，kk端口9092
    /**
     * kafka是一个分布式的基于发布/订阅的消息队列，主要应用于大数据实时处理领域
     * 异步，缓冲,削峰,解耦,可恢复性,高可用，高容错
     *
     * ====kafka 基础架构====：
     * 1、生产者
     * 2、消费者
     * 3、消费者组
     *
     *
     * ====kafka文件存储的方式====：
     * 1、一个topic分为多个partition(区)
     * 2、一个partition分为多个segment
     * 3、一个segment对应两个文件（.index & .log）
     * index和log文件以当前segment的第一条消息的offset命名
     *
     * ====kafka生产者====:
     * 1、分区的原因：
     *  1）、方便在集群中扩展：每个partition可以通过调整以适应所在的机器
     *  2）、提高并发能力：可以以partition为单位进行读写
     * 2、分区的原则：
     *  1）、在指明partition的情况下，直接将指明的值作为partition值
     *  2）、没有指明partition值，但有key的情况下，将key的 hash值与topic的partition进行取余得到partition
     *  3）、两者都没有的情况，轮询round-robin
     * 3、数据可靠性
     *  topic的每一个partition收到消息后都要发送一个ack
     *  1）、全部同步完成才发送ack，选举leader时，容忍n台节点故障，需要n+1个副本
     *  2）、ISR：Leader维护的一个动态的in-sync-replica set(ISR)，为了解决全部同步完成发送ack发生故障的情况，
     *  如果某follower迟迟未同步完成（未给leader发送同步完成ack），那么follower将被踢出ISR
     *  时间阈值由参数：replica.lag.time.max.ms设置，leader发生故障后，就会从ISR中重新选举leader
     * 4、ack应答机制
     *  对于某些不重要的数据，不一定要follower全部同步成功才ack
     *  故kafka提供了三种可靠性级别：
     *  acks参数配置：
     *  acks:
     *      0:最低延时，生产者不等待ack，broker还未持久化就返回，broker故障可能数据丢失
     *      1:leader接收，follower还未同步完成，就ack。follower同步完成前，leader故障，数据丢失
     *      -1:leader接收，follower同步完成，才ack。但follower同步完成后，broker发送ack前，leader故障，会造成数据重复
     *  5、数据一致性
     *  1）、HW机制（高水位机制）
     *  LEO：（Log End Offset）每个副本自大的offset
     *  HW:(High Watermark高水位)消费者能见最大offset，ISR队列中最小的LEO
     *  leadr    ===============HW===================LEO
     *  follower1===============HW(LEO)
     *  follower2===============HW======(LEO)
     *  只给消费者暴露同步完成的消息
     *  leader故障重新开始选举，直接截取HW前的数据开始同步
     *  HW机制：但是这个只能保证副本间的数据一致性，并不能保证数据不重复不丢失
     *  2）、Exactly Once
     *  ACK:
     *      -1:At least once（可能重复数据）
     *      0:At most once（可能丢失数据）
     *  幂等性解决上述两个问题：
     *  At Least Once +幂等性=Exactly Once
     *  enable.idompotent =true启用幂等性
     *  生产者初始化分配PID，发往同意partition会附带Seq Num，而Broker端会缓存
     *  <PID,partition，SeqNum>，相同主键进来会只持久化一条。
     *  但是，Pid重启会改变，不同的Partition有不同的主键，所以幂等性无法保证
     *  跨会话跨分区的Exactly Once
     *
     *  ====kafka消费者====
     *  1、采用pull模式，速率由消费者决定。如果broker没有数据，则陷入循环
     *  2、分区分配策略：
     *      1）、轮询(round-robin):按照组划分
     *      2）、范围(range):按照主题划分
     *  3、offset的维护
     */
}
