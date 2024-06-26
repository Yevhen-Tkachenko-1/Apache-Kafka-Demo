package yevhent.project.wikimedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.project.wikimedia.producer.WikimediaRecentchangeProducer;
import yevhent.project.wikimedia.source.WikimediaRecentchangeSource;

import java.util.concurrent.TimeUnit;

public class RecentchangeProducerMicroservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecentchangeProducerMicroservice.class);

    public static void main(String[] args) {

        LOGGER.info("Hello world!");

        try (WikimediaRecentchangeProducer producer = new WikimediaRecentchangeProducer();
             WikimediaRecentchangeSource source = new WikimediaRecentchangeSource(producer)) {

            source.start();
            TimeUnit.SECONDS.sleep(ApplicationProperty.PRODUCER_SERVICE_LIFE_SECONDS);

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            LOGGER.info("Goodbye world!");
        }
    }
}
