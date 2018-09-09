package onextent.akka.azure.ehtail

import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.{Done, NotUsed}
import com.typesafe.config.{Config, ConfigFactory}
import onextent.akka.azure.ehtail.Conf._
import onextent.akka.eventhubs.Connector.AckableOffset
import onextent.akka.eventhubs.EventHubConf
import onextent.akka.eventhubs.Eventhubs._
import org.json4s._
import org.json4s.native.JsonMethods._

import scala.concurrent.Future

object Run {

  def apply(): Unit = {

    val consumer: Sink[(String, AckableOffset), Future[Done]] =
      Sink.foreach(_._2.ack())

    val toConsumer = createToConsumer(consumer)

    val cfg: Config = ConfigFactory.load().getConfig("eventhubs-1")

    for (pid <- 0 until EventHubConf(cfg).partitions) {

      val src: Source[(String, AckableOffset), NotUsed] =
        createPartitionSource(pid, cfg)

      val flow =
        Flow[(String, AckableOffset)].map((x: (String, AckableOffset)) => {
          if (conf.getBoolean("main.pretty") && x._1.charAt(0) == '{') {
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

object Main extends App {

  Run()

}
