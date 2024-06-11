package yevhent.demo.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.ApplicationProperty;
import yevhent.demo.kafka.model.WikimediaRecentchange;

import java.io.Closeable;
import java.util.Properties;
import java.util.function.Consumer;

public class WikimediaRecentchangeProducer implements Consumer<WikimediaRecentchange>, Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentchangeProducer.class);
    private final KafkaProducer<String, String> kafkaProducer;

    public WikimediaRecentchangeProducer() {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", ApplicationProperty.KAFKA_BOOTSTRAP_SERVERS);
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        kafkaProducer = new KafkaProducer<>(properties);
    }

    @Override
    public void accept(WikimediaRecentchange message) {
        kafkaProducer.send(new ProducerRecord<>(ApplicationProperty.KAFKA_WIKIMEDIA_RECENTCHANGE_TOPIC, message.getTitle(), message.toString()),
                (metadata, exception) -> {
                    if (exception == null) {
                        LOGGER.info("Message produced: topic = {}, partition = {}, offset = {}, timestamp = {}, message = {}",
                                metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp(), message);
                    } else {
                        LOGGER.error("Error while message producing: {}", message);
                    }
                });
    }

    @Override
    public void close() {
        kafkaProducer.close();
    }
}
