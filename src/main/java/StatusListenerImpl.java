import org.joda.time.LocalDateTime;
import org.springframework.data.mongodb.core.MongoOperations;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class StatusListenerImpl implements StatusListener {

    private final MongoOperations mongoOperation;
    private List<String> tweets = new ArrayList<>();
    private ConcurrentMap<String, Integer> tweetWordCount = new ConcurrentHashMap<>();

    public StatusListenerImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    @Override
    public void onStatus(Status status) {
        String screenName = status.getUser().getScreenName();
        String text = status.getText();
//        System.out.println("@" + screenName + " /  ==> " + text);
        processTweet(status);
        if (tweets.size() % 100 == 0) {
            System.out.println("Tweets size = " + tweets.size());
            System.out.println("Tweets Count = " + tweetWordCount);
        }
    }

    private synchronized void processTweet(Status status) {
        String text = status.getText().toLowerCase();
        tweets.add(text);
        incCountFor(status, "aap");
        incCountFor(status, "kejriwal");
        incCountFor(status, "bjp");
        incCountFor(status, "namo");
        incCountFor(status, "modi");
        incCountFor(status, "kiran bedi");

    }

    private void incCountFor(Status status, String key) {
        String text = status.getText().toLowerCase();
        LocalDateTime dateTime = new LocalDateTime(status.getCreatedAt());

        if (text.contains(key)) {
            Integer integer = tweetWordCount.get(key);
            if (integer == null) {
                tweetWordCount.put(key, 1);
            } else {
                tweetWordCount.put(key, integer + 1);
            }

            mongoOperation.insert(new Tweet(text, key, dateTime.toString("yyyyMMdd"), dateTime.toString("HH")));
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        System.out.println("Got stall warning:" + warning);
    }

    @Override
    public void onException(Exception ex) {
        ex.printStackTrace();
    }
}
