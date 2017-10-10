package me.codebabe.server.mqc.processor

/**
  * author: code.babe
  * date: 2017-10-09 19:53
  */
class SparkProcessor extends MessageProcessor {
  /**
    * 元数据处理, 消息队列消息拿出来后做的相关处理
    *
    * @param meta
    */
  override def process(meta: Array[Byte]): Unit = {

  }
}
