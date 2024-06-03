package yevhent.demo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.KafkaProperty;

import java.util.Properties;

public class NullKeyStreamProducerDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(NullKeyStreamProducerDemo.class.getName());

    public static void main(String[] args) {

        LOGGER.info("Hello world!");

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", KafkaProperty.BOOTSTRAP_SERVERS);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("batch.size", "400");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = 0; i < 100; i++) {
            SimpleEventProducerDemo.send(producer, null, "Message from Java Producer: key = null reference, value = " + i);
            SimpleEventProducerDemo.send(producer, "null", "Message from Java Producer: key = 'null' string, value = " + i);
            SimpleEventProducerDemo.send(producer, "Message from Java Producer: key = not specified, value = " + i);
        }
        producer.close();
    }
}