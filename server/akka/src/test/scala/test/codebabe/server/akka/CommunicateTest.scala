package test.codebabe.server.akka

import actor.ChannelActor
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.util.ResourceUtils

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * author: code.babe
  * date: 2018-01-15 10:47
  */
object CommunicateTest extends App {

  try {
    val config: Config = ConfigFactory.parseFile(ResourceUtils.getFile("classpath:reference.conf"))
    val actorSystem: ActorSystem = ActorSystem.create("test", config)
    val channel = actorSystem.actorOf(Props[ChannelActor], name = "channelActor")
    println(channel.path.toString)
    channel ! Ping

    val selection = actorSystem.actorSelection("/user/channelActor")
    implicit val timeout: Timeout = Timeout(5 seconds)
    val kid = Await.result(selection.resolveOne(), Duration.Inf)
    kid ! Hi
  } catch {
    case e: Exception => e.printStackTrace()
  }

}
