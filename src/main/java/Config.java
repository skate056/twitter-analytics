import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Config {
    public static Configuration config() {
        return new ConfigurationBuilder()
                .setOAuthConsumerKey("OYM2HxgGiWvVoKgxm5ECgz7FC")
                .setOAuthConsumerSecret("xz8jMwnGMU7YkKyBhr76689QpdKCEL2GAacLzeqsUzuWFo94WZ")
                .setOAuthAccessToken("74458035-JwpgFM8R8WKmpFVh102tOo57RhsewHg4zTuMey6l8")
                .setOAuthAccessTokenSecret("2rp0ZttSnh1bPGmTbOYnb3REKgOFZWtIM3nKKLvPxlaTN")
                .build();
    }
}
