package yevhent.demo.kafka.wikimedia;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.ApplicationProperty;

import java.net.URI;
import java.util.concurrent.TimeUnit;

public class WikimediaRecentchangeSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentchangeSource.class);

    public static void sendRecentchanges(long seconds) {

        LOGGER.info("Hello world!");

        URI wikimediaURI = URI.create(ApplicationProperty.WIKIMEDIA_RECENTCHANGE_URI);
        BackgroundEventHandler eventHandler = new WikimediaRecentchangeHandler();

        try (BackgroundEventSource eventSource = new BackgroundEventSource.Builder(eventHandler, new EventSource.Builder(wikimediaURI)).build()) {
            eventSource.start();
            TimeUnit.SECONDS.sleep(seconds);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
