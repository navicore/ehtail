package onextent.akka.azure.ehtail

import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.{Done, NotUsed}
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}
import onextent.akka.azure.ehtail.Conf._
import onextent.akka.eventhubs.Connector.AckableOffset
import onextent.akka.eventhubs.EventHubConf
import onextent.akka.eventhubs.Eventhubs._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.rogach.scallop._

import scala.concurrent.Future

class CliConf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val partitions: ScallopOption[Int] = opt[Int](required = true)
  val offset: ScallopOption[String] = opt[String](required = false)
  val pretty: ScallopOption[Boolean] = opt[Boolean](required = false)
  val consumerGroup: ScallopOption[String] = opt[String](required = false)
  val connString: ScallopOption[String] = trailArg[String](required = true)
  verify()
}

object Go {

  def apply(cfg: Config, pp: Boolean): Unit = {

    val consumer: Sink[(String, AckableOffset), Future[Done]] =
      Sink.foreach(_._2.ack())

    val toConsumer = createToConsumer(consumer)

    for (pid <- 0 until EventHubConf(cfg).partitions) {

      val src: Source[(String, AckableOffset), NotUsed] =
        createPartitionSource(pid, cfg)

      val flow =
        Flow[(String, AckableOffset)].map((x: (String, AckableOffset)) => {
          if (pp && x._1.charAt(0) == '{') {
            val parsedJson: JValue = parse(x._1)
            println(
              s"consumer pid $pid received:\n${pretty(render(parsedJson))}")
          } else {
            println(s"consumer pid $pid received:\n${x._1}")
          }
          x
        })

      src.via(flow).runWith(toConsumer)

    }
  }

}

object Cli extends App {

  val conf = new CliConf(args)
  val pp: Boolean = conf.pretty()

  val p: Int = conf.partitions()
  val c: String = conf.consumerGroup.getOrElse("$Default")
  val o: String = conf.offset.getOrElse("LATEST")
  val s: String = conf.connString()

  val cfg: Config = ConfigFactory
    .load()
    .getConfig("eventhubs-1")
    .withValue("connection.partitions", ConfigValueFactory.fromAnyRef(p))
    .withValue("connection.consumerGroup", ConfigValueFactory.fromAnyRef(c))
    .withValue("connection.defaultOffset", ConfigValueFactory.fromAnyRef(o))
    .withValue("connection.connStr", ConfigValueFactory.fromAnyRef(s))

  Go(cfg, pp)

}
