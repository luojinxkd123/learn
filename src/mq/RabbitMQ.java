package mq;

/**
 * @author:luojin
 * @apiNote:
 * @since: 2020-11-02 15:44
 */
public class RabbitMQ {
    /**
     * 除了@RabbitListener （标在类+方法上）可以接收消息
     * 还有 @RabbitHandler （标在方法上） 可以
     * RabbitListener接受到的消息交给RabbitHandler去处理，RabbitHandler能区分不同的消息
     *两者搭配使用，具体交给哪个RabbitHandler去处理是由MessageConverter转换结果去决定的
     */

    /**
     * 消息可靠性的保证：
     * 保证消息不丢失、可靠抵达，可以使用事务消息，性能下降250倍，为此引入确认机制
     *
     * Publisher==(confirmCallback)==>Broker[Exchange==(returnCallback)==>Queue]==(ack)==>Consumer
     *
     * 1、spring.rabbitmq.publisher-confirms=true(开启发送端抵达broker确认)
     * rabbitTemplate.setConfirmCallback(RabbitTemplate.ConfirmCallback confirmCallback)
     * （设置投递broker确认回调）
     * 保证投递到broker
     * （集群下需要所有broker接收到才会调用confirmCallback、
     *  被broker接收到只能表示message已经到达服务器，并不能保证消息一定会被投递到queue，
     *  所以需要接下来returnCallback）
     *
     * 2、spring.rabbitmq.publisher-returns=true(开启发送端消息抵达队列确认)
     * spring.rabbitmq.templete.mandatory=true
     * rabbitTemplate.setReturnCallback(RabbitTemplate.ReturnCallback returnCallback)
     * (设置消息抵达队列的确认回调==>投递成功不会触发)
     * 保证投递到queue
     *
     * 3、消费端确认（保证每一个消息正确消费,此时才可以broker删除这个消息）
     *  问题：
     *      我们收到很多消息，自动回复给服务器ack，服务器宕机了，造成消息丢失？？？
     *  解决：
     *      spring.rabbitmq.listener.simple.acknowledge-mode=manual(手动确认)
     *      未被签收，就一直unacked状态，即使服务器宕机，也不会丢失，回到ready状态
     *      签收货物：
     *      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
     *      （false表示非批量模式签收）
     *      拒签货物：
     *      channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, 是否重回队列true);
     *      （退货）nack和reject的区别在于reject不能批量签收
     *      只要没有调用ack，则消息会一直出于unacked状态
     *
     */
}
