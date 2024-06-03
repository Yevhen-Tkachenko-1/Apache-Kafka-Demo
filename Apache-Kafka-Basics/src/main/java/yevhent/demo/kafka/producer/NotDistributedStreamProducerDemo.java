package yevhent.demo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.KafkaProperty;

import java.util.Properties;

public class NotDistributedStreamProducerDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotDistributedStreamProducerDemo.class.getName());

    public static void main(String[] args) {

        LOGGER.info("Hello world!");

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", KafkaProperty.BOOTSTRAP_SERVERS);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("batch.size", "400");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String key = "123";
        for (int i = 0; i < 100; i++) {
            String message = "Message from Java Producer: key = " + key + ", value = " + i;
            SimpleEventProducerDemo.send(producer, key, message);
        }
        producer.close();
    }
}