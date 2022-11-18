package com.huoranger.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author: 清风徐来
 * @date: 11/18/2022 10:27 AM
 * @description:
 */
public class InterceptorProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 设置生产者Broker服务器地址
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.31.235:9092");
        // 设置序列化key程序类
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置序列化value程序类
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 设置拦截器链,需指定全路径
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, Arrays.asList("com.huoranger.interceptor.TimeInterceptor", "com.huoranger.interceptor.CounterInterceptor"));

        // 定义消息生产者
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);


        // 发送消息方式，消息发送给服务器即发送完成，而不管消息是否送达
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send(new ProducerRecord<String,String>("topic-test","hello kafka " + i));
        }

        // 调用该方法将触发拦截器的close()方法
        kafkaProducer.close();

    }
}
