import twitter4j._

object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("OYM2HxgGiWvVoKgxm5ECgz7FC")
    .setOAuthConsumerSecret("xz8jMwnGMU7YkKyBhr76689QpdKCEL2GAacLzeqsUzuWFo94WZ")
    .setOAuthAccessToken("74458035-JwpgFM8R8WKmpFVh102tOo57RhsewHg4zTuMey6l8")
    .setOAuthAccessTokenSecret("2rp0ZttSnh1bPGmTbOYnb3REKgOFZWtIM3nKKLvPxlaTN")
    .build


  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) { println(status.getUser.getScreenName + "::" + status.getText) }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }

}

