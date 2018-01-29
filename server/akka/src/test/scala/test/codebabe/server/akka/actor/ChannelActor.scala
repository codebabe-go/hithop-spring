package test.codebabe.server.akka.actor

import akka.actor.{Actor, ReceiveTimeout}
import test.codebabe.server.akka.{Ping, Pong}

import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * author: code.babe
  * date: 2018-01-16 13:48
  */
class ChannelActor extends Actor {
  override def receive: Receive = {
    case Ping =>
      context.setReceiveTimeout(5 second)
      println(s"${System.currentTimeMillis()}-ping")
      Thread.sleep(10000)
    case Pong =>
      println(s"${System.currentTimeMillis()}-pong")
    case ReceiveTimeout =>
      println(s"${System.currentTimeMillis()}-tiemout")
  }
}
