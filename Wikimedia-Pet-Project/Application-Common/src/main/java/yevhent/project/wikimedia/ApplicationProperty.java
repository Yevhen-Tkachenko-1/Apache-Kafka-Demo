package yevhent.project.wikimedia;

public class ApplicationProperty {

    public static final String WIKIMEDIA_RECENTCHANGE_URI = "https://stream.wikimedia.org/v2/stream/recentchange";

    public static final long PRODUCER_SERVICE_LIFE_SECONDS = 10;

    public static final String KAFKA_BOOTSTRAP_SERVERS = "[::1]:9092";
    public static final String KAFKA_WIKIMEDIA_RECENTCHANGE_TOPIC = "wikimedia.recentchange";
    public static final String KAFKA_CONSUMER_GROUP = "wikimedia.recentchange.consumer";


}
