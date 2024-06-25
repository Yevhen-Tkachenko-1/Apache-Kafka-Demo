package yevhent.project.wikimedia.source;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import yevhent.project.wikimedia.ApplicationProperty;
import yevhent.project.wikimedia.model.WikimediaRecentchange;

import java.io.Closeable;
import java.net.URI;
import java.util.function.Consumer;

public class WikimediaRecentchangeSource implements Closeable {

    private final BackgroundEventSource eventSource;

    public WikimediaRecentchangeSource(Consumer<WikimediaRecentchange> eventConsumer) {
        URI wikimediaURI = URI.create(ApplicationProperty.WIKIMEDIA_RECENTCHANGE_URI);
        BackgroundEventHandler eventHandler = new WikimediaRecentchangeHandler(eventConsumer);

        eventSource = new BackgroundEventSource.Builder(eventHandler, new EventSource.Builder(wikimediaURI)).build();
    }

    public void start() {
        eventSource.start();
    }

    @Override
    public void close() {
        eventSource.close();
    }
}
