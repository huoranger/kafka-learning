package com.huoranger.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author: 清风徐来
 * @date: 11/18/2022 10:20 AM
 * @description:
 */
public class MessageConsumerB {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 设置消费者Broker服务器地址
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.31.235:9092");
        // 设置反序列化key程序类，与生产者对应
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 设置反序列化value程序类，与生产者对应
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        // 设置消费者组ID，组名称相同的消费者属于同一个消费者组
        // 创建不属于任何一个消费者组的消费者也可以，但是不常见。
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"groupID-2");

        // 创建消费者
        KafkaConsumer<String, Integer> kafkaConsumer = new KafkaConsumer<>(properties);

        // 设置消费者读取的主题名称，可以设置多个
        kafkaConsumer.subscribe(Arrays.asList("topic-test"));

        // 不停读取消息
        while(true){
            // 拉取消息，并设置超时时间为10秒
            ConsumerRecords<String, Integer> records = kafkaConsumer.poll(Duration.ofSeconds(10));
            // 打印消息
            for (ConsumerRecord<String, Integer> record : records) {
                System.out.println("key: "+record.key()+",value: "+record.value()+", partition: "+record.partition()+",offset: "+record.offset());
            }
        }
    }
}
