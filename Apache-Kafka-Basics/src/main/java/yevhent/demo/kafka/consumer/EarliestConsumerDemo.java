package yevhent.demo.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.KafkaProperty;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

/**
 * <b>AUTO_OFFSET_RESET</b>
 * <br>
 * <br>What to do when there is no initial offset in Kafka
 * or if the current offset does not exist any more on the server
 * (e.g. because that data has been deleted).
 *
 * <ul><li>earliest: automatically reset the offset to the earliest offset</li>
 * <li>latest: automatically reset the offset to the latest offset</li>
 * <li>none: throw exception to the consumer if no previous offset is found for the consumer's group</li>
 * <li>anything else: throw exception to the consumer.</li></ul>
 * <p>
 * Note that altering partition numbers while setting this config to latest may cause message delivery loss since
 * producers could start to send messages to newly added partitions (i.e. no initial offsets exist yet) before consumers reset their offsets.
 */

public class EarliestConsumerDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(EarliestConsumerDemo.class.getName());

    /**
     * The same as <b>--from-beginning</b> parameter in CLI request to kafka-console-consumer.sh
     */
    public static final String AUTO_OFFSET_RESET_EARLIEST = "earliest";

    public static void main(String[] args) {

        LOGGER.info("Hello world!");

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperty.BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "java-earliest");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_EARLIEST);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(KafkaProperty.DEFAULT_TOPIC));

        for (int i = 0; i < 2; i++) {

            LOGGER.info("Pulling messages " + i);

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));

            int j = 0;
            for (ConsumerRecord<String, String> record : records) {
                LOGGER.info("Pulled record[{}][{}]: topic = {}, partition = {}, offset = {}, timestamp = {}, key = {}, value = {}",
                        i, j++, record.topic(), record.partition(), record.offset(), record.timestamp(), record.key(), record.value());
            }
        }
        consumer.close();
    }
}
