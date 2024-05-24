package yevhent.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SimpleProducerDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleProducerDemo.class.getName());

    public static void main(String[] args) {
        System.out.println("Hello world!");
        LOGGER.info("Hello world!");

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "[::1]:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "Message from Java Producer");
        producer.send(record, (metadata, exception)-> {
            if(exception == null){
                LOGGER.info("Metadata: topic = {}, partition = {}, offset = {}, timestamp = {}",
                        metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            } else {
                LOGGER.error("Error while producing", exception);
            }
        });
        producer.close();
    }
}