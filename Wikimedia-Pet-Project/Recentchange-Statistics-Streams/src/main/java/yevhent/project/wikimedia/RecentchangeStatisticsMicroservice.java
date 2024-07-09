package yevhent.project.wikimedia;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.project.wikimedia.model.WikimediaRecentchange;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class RecentchangeStatisticsMicroservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecentchangeStatisticsMicroservice.class);

    public static void main(String[] args) throws InterruptedException {
        LOGGER.info("Hello world!");

        Topology topology = buildTopology();
        LOGGER.info("Topology: {}", topology.describe());

        try (KafkaStreams streams = buildStatisticsStreams(topology)) {
            streams.start();
            TimeUnit.SECONDS.sleep(ApplicationProperty.STATISTICS_STREAMS_SERVICE_LIFE_SECONDS);
        } finally {
            LOGGER.info("Goodbye world!");
        }
    }

    private static Topology buildTopology() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        KStream<String, String> recentchangeStream = streamsBuilder.stream(ApplicationProperty.KAFKA_WIKIMEDIA_RECENTCHANGE_TOPIC);
        streamRecentchangeType(recentchangeStream);
        return streamsBuilder.build();
    }

    private static void streamRecentchangeType(KStream<String, String> inputStream) {
        inputStream.mapValues(event -> new WikimediaRecentchange(event).getType())
                .groupBy((key, type) -> type)
                .count(Materialized.as(ApplicationProperty.KAFKA_STATISTIC_TYPE_STORE))
                .toStream()
                .mapValues((key, value) -> {
                    LOGGER.info("Stream Counted: type = {}, number = {}", key, value);
                    return new JSONObject(Map.of(key, value)).toString();
                })
                .to(ApplicationProperty.KAFKA_STATISTIC_TYPE_TOPIC);
    }

    private static KafkaStreams buildStatisticsStreams(Topology topology) {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, RecentchangeStatisticsMicroservice.class.getSimpleName());
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationProperty.KAFKA_BOOTSTRAP_SERVERS);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return new KafkaStreams(topology, properties);
    }

}