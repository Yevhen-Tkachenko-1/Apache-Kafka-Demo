package yevhent.demo.kafka.wikimedia;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikimediaRecentchangeHandler implements BackgroundEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentchangeHandler.class);

    private static int counter = 0;

    @Override
    public void onOpen() {
        LOGGER.info("Open.");
    }

    @Override
    public void onClosed() {
        LOGGER.info("Closed.");
    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) {
        LOGGER.info("Handling message[{}]: data = {}",
                counter, messageEvent.getData());
        counter++;
    }

    @Override
    public void onComment(String s) {
        LOGGER.info("Commented.");
    }

    @Override
    public void onError(Throwable throwable) {
        LOGGER.error("Error while handling message[{}]: {}", counter, throwable.getMessage());
    }
}
