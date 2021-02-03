package com.eightlegend.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * 批量发送消息
 * Messages of the same batch should have: same topic, same waitStoreMsgOK and no schedule support.
 * Besides, the total size of the messages in one batch should be no more than 1MiB.
 */
public class SimpleBatchProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("BatchProducer");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        //如果一次只发送不超过1M的消息，那么批处理很容易使用
        //同一批的消息应该有：相同的主题，相同的waitstoremsgok，不支持调度
        String topic = "TopicTest";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "Tag", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "Tag", "OrderID003", "Hello world 2".getBytes()));

        producer.send(messages);
        System.out.printf("Batch over");
        producer.shutdown();
    }
}
