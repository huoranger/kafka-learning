package com.huoranger.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: 清风徐来
 * @date: 11/17/2022 5:33 PM
 * @description:
 */
public class MessageProducerA {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        // 设置生产者Broker服务器地址
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.31.235:9092");
        // 设置序列化key程序类
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置序列化value程序类，不一定是Integer，可以是String
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());

        // 定义消息生产者
        KafkaProducer<String, Integer> kafkaProducer = new KafkaProducer<>(properties);

        // 发送消息方式，消息发送给服务器即发送完成，而不管消息是否送达
        for (int i = 0; i < 10; i++) {
            Future<RecordMetadata> result = kafkaProducer.send(new ProducerRecord<String, Integer>("topic-test", "hello kafka " + i, i));
            System.out.println(result.get());
        }
        kafkaProducer.close();
    }
}
