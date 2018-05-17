package test.codebabe.server.akka

/**
  * author: code.babe
  * date: 2018-01-16 13:49
  */
sealed trait Message

case object Ping extends Message
case object Pong extends Message
case object Hi extends Message
