package onextent.akka.azure.ehtail

import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.{Done, NotUsed}
import com.typesafe.config.{Config, ConfigFactory}
import onextent.akka.azure.ehtail.Conf._
import onextent.akka.eventhubs.Connector.AckableOffset
import onextent.akka.eventhubs.EventHubConf
import onextent.akka.eventhubs.Eventhubs._

import scala.concurrent.Future

object MultiPartitionExample {

  def apply(): Unit = {

    val consumer: Sink[(String, AckableOffset), Future[Done]] =
      Sink.foreach(m => {
        //println(s"SUPER SOURCE: ${m._1.substring(0, 160)}")
        //println(s"SINGLE SOURCE: ${m._1}")
        m._2.ack()
      })

    val toConsumer = createToConsumer(consumer)

    val cfg: Config = ConfigFactory.load().getConfig("eventhubs-1")

    for (pid <- 0 until EventHubConf(cfg).partitions) {

      val src: Source[(String, AckableOffset), NotUsed] =
        createPartitionSource(pid, cfg)

      val flow = Flow[(String, AckableOffset)].map((x: (String, AckableOffset)) => {
        //println(s"do something! pid: $pid ${x._1.substring(0, 9)}")
        if (conf.getBoolean("main.pretty") && x._1.charAt(0) == '{') {
          import org.json4s._
          import org.json4s.native.JsonMethods._
          val parsedJson: JValue = parse(x._1)
          println(s"consumer pid $pid received:\n${pretty(render(parsedJson))}")
        } else {
          println(s"consumer pid $pid received:\n${x._1}")
        }

        x
      })

//      val xform = Flow[(String, AckableOffset)].map((x: (String, AckableOffset)) => {
//
//        import onextent.data.navipath.dsl.NaviPathSyntax._
//        val txnId = x._1.query[String]("$.someId")
//        println(s"do something with txnid! $txnId")
//        (txnId.getOrElse("unknown"), x._2)
//      })

      src.via(flow).runWith(toConsumer)
      //src.via(xform).runWith(toConsumer)

    }
  }

}

object SinglePartitionExample {

  def apply(): Unit = {

    val cfg: Config = ConfigFactory.load().getConfig("eventhubs-1")

    val source1 = createPartitionSource(0, cfg)

    source1.runForeach(m => {
      println(s"SINGLE SOURCE: ${m._1.substring(0, 160)}")
      m._2.ack()
    })

  }

}

object Main extends App {

  // TODO: create a toFlow example

  MultiPartitionExample()
  //SinglePartitionExample()

}
