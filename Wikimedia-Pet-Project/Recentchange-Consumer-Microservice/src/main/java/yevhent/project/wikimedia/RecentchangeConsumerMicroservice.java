package yevhent.project.wikimedia;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

public class RecentchangeConsumerMicroservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecentchangeConsumerMicroservice.class);

    public static void main(String[] args) {
        LOGGER.info("Hello world!");

        KafkaConsumer<String, String> consumer = ApplicationFactory.createKafkaConsumer();

        RestHighLevelClient openSearchClient = ApplicationFactory.createOpenSearchClient();

        try (consumer; openSearchClient) {
            for (int i = 0; i < 5; i++) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));

                int j = 0;
                for (ConsumerRecord<String, String> record : records) {
                    LOGGER.info("Pulled record[{}][{}]: topic = {}, partition = {}, offset = {}, timestamp = {}, key = {}, value = {}",
                            i, j++, record.topic(), record.partition(), record.offset(), record.timestamp(), record.key(), record.value());
                    IndexResponse response = openSearchClient.index(
                            new IndexRequest(ApplicationProperty.OPENSEARCH_WIKIMEDIA_RECENTCHANGE_INDEX).source(record.value(), XContentType.JSON),
                            RequestOptions.DEFAULT);
                    LOGGER.info("Sent to OpenSearch: index = {}, _doc = {}, result = {}", response.getIndex(), response.getId(), response.getResult());
                }
                Thread.sleep(3000);
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("Goodbye world!");
        }


    }

}