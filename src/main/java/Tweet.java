import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tweet2")
public class Tweet {
    private final String tweet;
    private final String tag;
    private final String date;
    private final String timeHour;

    public Tweet(String tweet, String tag, String date, String timeHour) {
        this.tweet = tweet;
        this.tag = tag;
        this.date = date;
        this.timeHour = timeHour;
    }
}
