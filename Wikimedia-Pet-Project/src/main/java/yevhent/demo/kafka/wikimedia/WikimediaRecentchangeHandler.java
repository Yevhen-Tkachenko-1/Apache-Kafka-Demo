package yevhent.demo.kafka.wikimedia;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yevhent.demo.kafka.model.WikimediaRecentchange;

import java.util.function.Consumer;

public class WikimediaRecentchangeHandler implements BackgroundEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentchangeHandler.class);

    private static int counter = 0;

    private final Consumer<WikimediaRecentchange> eventConsumer;

    public WikimediaRecentchangeHandler(Consumer<WikimediaRecentchange> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

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
        WikimediaRecentchange wikimediaRecentchange = new WikimediaRecentchange(messageEvent.getData());
        LOGGER.info("Handling message[{}]: data = {}", counter, wikimediaRecentchange);
        eventConsumer.accept(wikimediaRecentchange);
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
