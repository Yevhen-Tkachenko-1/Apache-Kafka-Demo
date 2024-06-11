package yevhent.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.wikimedia.WikimediaRecentchangeSource;

public class WikimediaProducerMicroservice {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaProducerMicroservice.class);

    public static void main(String[] args) {

        LOGGER.info("Hello world!");

        WikimediaRecentchangeSource.sendRecentchanges(10);
    }
}
