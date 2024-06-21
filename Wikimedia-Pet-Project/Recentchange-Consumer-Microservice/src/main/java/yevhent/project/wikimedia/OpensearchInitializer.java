package yevhent.project.wikimedia;

import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.opensearch.client.indices.GetIndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class OpensearchInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpensearchInitializer.class);

    public static void main(String[] args) {
        LOGGER.info("Hello world!");

        initializeIndex();
        listIndex();
    }

    static void initializeIndex() {
        RestHighLevelClient client = ApplicationFactory.createOpenSearchClient();

        try (client) {
            if (client.indices().exists(new GetIndexRequest(ApplicationProperty.OPENSEARCH_WIKIMEDIA_RECENTCHANGE_INDEX), RequestOptions.DEFAULT)) {
                LOGGER.warn("Index already exists: " + ApplicationProperty.OPENSEARCH_WIKIMEDIA_RECENTCHANGE_INDEX);
                return;
            }
            client.indices().create(new CreateIndexRequest(ApplicationProperty.OPENSEARCH_WIKIMEDIA_RECENTCHANGE_INDEX), RequestOptions.DEFAULT);
            LOGGER.info("Index initialized: " + ApplicationProperty.OPENSEARCH_WIKIMEDIA_RECENTCHANGE_INDEX);

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    static void listIndex() {
        RestHighLevelClient client = ApplicationFactory.createOpenSearchClient();

        try (client) {
            GetIndexResponse response = client.indices().get(new GetIndexRequest("_all"), RequestOptions.DEFAULT);
            LOGGER.info("Existing indexes: " + Arrays.toString(response.getIndices()));

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
