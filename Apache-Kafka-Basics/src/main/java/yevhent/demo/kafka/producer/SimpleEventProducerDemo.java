package yevhent.demo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.KafkaProperty;

import java.util.Properties;

public class SimpleEventProducerDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventProducerDemo.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Hello world!");

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", KafkaProperty.BOOTSTRAP_SERVERS);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        send(producer, "Message from Java Producer");

        producer.close();
    }

    public static void send(KafkaProducer<String, String> producer, String message) {

        producer.send(new ProducerRecord<>(KafkaProperty.DEFAULT_TOPIC, message), (metadata, exception) -> {
            LOGGER.info("Sending Event: " + message);
            if (exception == null) {
                LOGGER.info("Metadata: topic = {}, partition = {}, offset = {}, timestamp = {}",
                        metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            } else {
                LOGGER.error("Error while producing", exception);
            }
        });
    }

    public static void send(KafkaProducer<String, String> producer, String key, String message) {

        producer.send(new ProducerRecord<>(KafkaProperty.DEFAULT_TOPIC, key, message), (metadata, exception) -> {
            LOGGER.info("Sending Event: " + message);
            if (exception == null) {
                LOGGER.info("Metadata: topic = {}, partition = {}, offset = {}, timestamp = {}",
                        metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            } else {
                LOGGER.error("Error while producing", exception);
            }
        });
    }

}