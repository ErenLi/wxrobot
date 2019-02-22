package com.eren.wxrobot.kafka;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

/**
 * @author linan
 * @since 2019/2/20
 */
public class ProducerTest {

    private void execMsgSend() throws  Exception{
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "1");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> procuder = new KafkaProducer<String, String>(props);

        String topic = "test";
        for (int i = 1; i <= 10; i++) {
            String value = " this is another message_" + i;
            ProducerRecord<String,String> record = new ProducerRecord<String, String>(topic,"1",value);
            procuder.send(record,new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset());
                }
            });
            System.out.println(i+" ----   success");
            Thread.sleep(1000);
        }
        System.out.println("send message over.");
        procuder.close();
    }
    public static void main(String[] args) throws  Exception{
        ProducerTest test1 = new ProducerTest();
        test1.execMsgSend();
    }

}
