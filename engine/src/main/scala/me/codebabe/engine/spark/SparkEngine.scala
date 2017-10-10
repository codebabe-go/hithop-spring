package me.codebabe.engine.spark

import java.io.InputStreamReader
import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

/**
  * author: code.babe
  * date: 2017-10-10 10:38
  */
object SparkEngine {

  private val logger = LoggerFactory.getLogger("SparkEngine")

  def getEngine(): SparkContext = {
    this.synchronized({
      new SparkContext(load())
    })
  }

  private def load(): SparkConf = {
    val conf = new SparkConf()
    try {
      // 这个配置会覆盖你系统中的spark默认配置
      val prop = new Properties()
      prop.load(new InputStreamReader(this.getClass.getClassLoader.getResourceAsStream("properties/spark.properties")))
      while (prop.elements().hasMoreElements && prop.keys().hasMoreElements) { // 这里总不会死循环吧?
        conf.set(prop.keys().nextElement().asInstanceOf[String], prop.elements().nextElement().asInstanceOf[String])
      }
    } catch {
      case e: Exception => logger.error(s"load configuration error", e)
    } finally {
      conf
        .setMaster("local[2]")
        .setAppName("sparkEngine")
    }
    conf
  }

}