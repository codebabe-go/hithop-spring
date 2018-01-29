package test.codebabe.server.akka

import actor.ChannelActor
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.util.ResourceUtils

/**
  * author: code.babe
  * date: 2018-01-15 10:47
  */
object CommunicateTest extends App {

  val config: Config = ConfigFactory.parseFile(ResourceUtils.getFile("classpath:config/reference.conf"))
  val actorSystem: ActorSystem = ActorSystem.create("test", config)
  val channel = actorSystem.actorOf(Props[ChannelActor], name = "channelActor")
  channel ! Ping
}
