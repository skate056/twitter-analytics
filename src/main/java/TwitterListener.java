import org.springframework.data.mongodb.core.MongoOperations;
import twitter4j.FilterQuery;
import twitter4j.RateLimitStatus;
import twitter4j.RateLimitStatusEvent;
import twitter4j.RateLimitStatusListener;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TwitterListener {
    private App.Parameter param;
    private MongoOperations mongoOperation;

    public TwitterListener(MongoOperations mongoOperation, App.Parameter param) {
        this.mongoOperation = mongoOperation;
        this.param = param;
    }

    public void run() {
        StatusListener listener = new StatusListenerImpl(mongoOperation);
        TwitterStream twitterStream = new TwitterStreamFactory(Config.config()).getInstance();
        twitterStream.addListener(listener);
// filter() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(new FilterQuery(0, param.follow, param.track));
        twitterStream.addRateLimitStatusListener(new RateLimitStatusListener() {
            @Override
            public void onRateLimitStatus(RateLimitStatusEvent event) {
                printStatus(event.getRateLimitStatus());
            }

            @Override
            public void onRateLimitReached(RateLimitStatusEvent event) {
                printStatus(event.getRateLimitStatus());

            }

            private void printStatus(RateLimitStatus rateLimitStatus) {
                System.out.println("Rate:" + rateLimitStatus.getRemaining() + "/" + rateLimitStatus.getLimit() + "  Reset in " + rateLimitStatus.getSecondsUntilReset() + " seconds");
            }
        });
    }
}
