package yevhent.demo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.KafkaProperty;

import java.util.Properties;

public class KeyedStreamProducerDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyedStreamProducerDemo.class.getName());

    public static void main(String[] args) {

        LOGGER.info("Hello world!");

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", KafkaProperty.BOOTSTRAP_SERVERS);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int i = -10; i <= 10; i++) {
            String key = getKey(i);
            String message = "Message from Java Producer: key = " + key + ", value = [1] " + i;
            SimpleEventProducerDemo.send(producer, key, message);
        }
        for (int i = -10; i <= 10; i++) {
            String key = getKey(i);
            String message = "Message from Java Producer: key = " + key + ", value = [2] " + i;
            SimpleEventProducerDemo.send(producer, key, message);
        }

        producer.close();
    }

    static String getKey(int number) {
        if (isRound(number)) {
            return null;
        }
        return getSign(number) + "_" + getParity(number);
    }

    static String getParity(int number) {
        return number % 2 == 0 ? "even" : "odd";
    }

    static boolean isRound(int number) {
        return number % 10 == 0;
    }

    static String getSign(int number) {
        if (number == 0) {
            return "zero";
        }
        return number < 0 ? "negative" : "positive";
    }

}