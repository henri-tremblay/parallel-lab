
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import akka.util.duration._
import bootstrap._

class PricingSimulation extends Simulation {
  val port = 8080
  val users = 100
  val duration = 30

  val httpConf = httpConfig
    .baseURL("http://localhost:" + port)
    .acceptHeader("application/json, text/plain, */*")
    .acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.3")
    .acceptEncodingHeader("gzip,deflate,sdch")
    .acceptLanguageHeader("en-US,en;q=0.8,fr;q=0.6")
    .connection("keep-alive")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
    .warmUp("http://localhost:" + port + "/index.html")

  val headers_13 = Map(
    "Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"""
  )

  val headers_24 = Map(
    "X-Requested-With" -> """XMLHttpRequest"""
  )

  val strikeFeeder = new Feeder[String] {

    import scala.util.Random

    private val RNG = new Random

    // random number in between [a...b]
    private def randInt = RNG.nextInt(200)

    // always return true as this feeder can be polled infinitively
    override def hasNext = true

    override def next: Map[String, String] = {
      val strike = randInt
      Map("strike" -> strike.toString)
    }
  }

  val scn = scenario("Pricing")
    .during(duration seconds) {
    feed(csv("maturity.csv").random)
      .feed(csv("stock.csv").random)
      .feed(strikeFeeder)
      .exec(http("price")
      .get("/services/pricer/price?symbol=${symbol}&maturity=${maturity}&strike=${strike}")
      .headers(headers_24)
      .check(status.is(200)))
  }

  setUp(scn.users(users).ramp(5 milliseconds).protocolConfig(httpConf))
}