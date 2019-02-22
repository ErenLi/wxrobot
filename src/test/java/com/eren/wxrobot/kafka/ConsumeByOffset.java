package com.eren.wxrobot.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author linan
 * @since 2019/2/20
 */
public class ConsumeByOffset {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.5.12:32401");
        props.put("group.id", "1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        TopicPartition p = new TopicPartition("payne-dev", 0);
//        指定消费topic的那个分区
        consumer.assign(Arrays.asList(p));
//        指定从topic的分区的某个offset开始消费
        consumer.seekToBeginning(Arrays.asList(p));
        consumer.seek(p, 0);
//        consumer.subscribe(Arrays.asList("test2"));

//        如果是之前不存在的group.id
//        Map<TopicPartition, OffsetAndMetadata> hashMaps = new HashMap<TopicPartition, OffsetAndMetadata>();
//        hashMaps.put(new TopicPartition("test2", 0), new OffsetAndMetadata(0));
//        consumer.commitSync(hashMaps);
//        consumer.subscribe(Arrays.asList("test2"));
        while (true) {
            ConsumerRecords<String, String> c = consumer.poll(100);
            for (ConsumerRecord<String, String> c1 : c) {
//                System.out.println("Key: " + c1.key() + " Value: " + c1.value() + " Offset: " + c1.offset() + " Partitions: " + c1.partition());
                System.out.println(JSON.parseObject(c1.value()));

            }
        }
    }
}
