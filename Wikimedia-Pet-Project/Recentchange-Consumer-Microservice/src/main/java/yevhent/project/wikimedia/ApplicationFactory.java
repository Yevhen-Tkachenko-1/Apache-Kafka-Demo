package yevhent.project.wikimedia;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;

import java.net.URI;
import java.util.List;
import java.util.Properties;

public class ApplicationFactory {

    public static RestHighLevelClient createOpenSearchClient() {

        URI uri = URI.create("http://localhost:9200");
        if (uri.getUserInfo() == null) {
            // REST client without security
            return new RestHighLevelClient(RestClient.builder(new HttpHost(uri.getHost(), uri.getPort(), "http")));
        }
        // REST client with security
        String[] auth = uri.getUserInfo().split(":");
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth[0], auth[1]));
        return new RestHighLevelClient(RestClient
                .builder(new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme()))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));
    }

    public static KafkaConsumer<String, String> createKafkaConsumer(){

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationProperty.KAFKA_BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ApplicationProperty.KAFKA_CONSUMER_GROUP);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(List.of(ApplicationProperty.KAFKA_WIKIMEDIA_RECENTCHANGE_TOPIC));
        return kafkaConsumer;
    }

}
