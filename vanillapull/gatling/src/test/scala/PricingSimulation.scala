
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import akka.util.duration._
import bootstrap._
import assertions._

class PricingSimulation extends Simulation {
  val port = 9090

  val httpConf = httpConfig
    .baseURL("http://localhost:"+port)
    .acceptHeader("application/json, text/plain, */*")
    .acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.3")
    .acceptEncodingHeader("gzip,deflate,sdch")
    .acceptLanguageHeader("en-US,en;q=0.8,fr;q=0.6")
    .connection("keep-alive")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
    .warmUp("http://localhost:"+port+"/index.html")

  val headers_13 = Map(
    "Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"""
  )

  val headers_24 = Map(
    "X-Requested-With" -> """XMLHttpRequest"""
  )

  val scn = scenario("Pricing")
    .repeat(20) {
        exec(http("price")
        .get("/services/pricer/price?symbol=BNP&maturity=90&strike=17")
        .headers(headers_24)
        .check(status.is(200)))
    }

  setUp(scn.users(50).ramp(5 milliseconds).protocolConfig(httpConf))
}