package yevhent.project.wikimedia;

public class ApplicationProperty {

    public static final String WIKIMEDIA_RECENTCHANGE_URI = "https://stream.wikimedia.org/v2/stream/recentchange";

    public static final long PRODUCER_SERVICE_LIFE_SECONDS = 30;
    public static final long STATISTICS_STREAMS_SERVICE_LIFE_SECONDS = 60;

    public static final String KAFKA_BOOTSTRAP_SERVERS = "[::1]:9092";
    public static final String KAFKA_WIKIMEDIA_RECENTCHANGE_TOPIC = "wikimedia.recentchange";
    public static final String KAFKA_STATISTIC_TYPE_TOPIC = "wikimedia.recentchange.statistics.type";
    public static final String KAFKA_STATISTIC_TYPE_STORE = "statistics-type-store";
    public static final String KAFKA_CONSUMER_GROUP = "wikimedia.recentchange.consumer";

    public static final String OPENSEARCH_WIKIMEDIA_RECENTCHANGE_INDEX = "wikimedia.recentchange";

}
