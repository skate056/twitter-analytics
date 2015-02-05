import twitter4j.{FilterQuery, TwitterStreamFactory}

object StatusStreamer extends App {
  println("Hi")

  val twitterStream = new TwitterStreamFactory(Util.config).getInstance
  twitterStream.addListener(Util.simpleStatusListener)
  twitterStream.filter(new FilterQuery(0, Array(), Array("aap", "#aap")))
  twitterStream.sample()
  Thread.sleep(5000)
  twitterStream.cleanUp()
  twitterStream.shutdown()
}
