package me.codebabe.engine.spark

import java.io.InputStreamReader
import java.util.Properties

import org.apache.spark.{SparkConf, SparkContext}

/**
  * author: code.babe
  * date: 2017-10-10 10:38
  */
object SparkEngine {

  def getEngine(): SparkContext = {
    this.synchronized({
      new SparkContext(load())
    })
  }

  private def load(): SparkConf = {
    val conf = new SparkConf(false)
    try {
      // 这个配置会覆盖你系统中的spark默认配置
      val prop = new Properties()
      prop.load(new InputStreamReader(this.getClass.getClassLoader.getResourceAsStream("properties/spark.properties")))
//      while (prop.elements().hasMoreElements && prop.keys().hasMoreElements) {// 这里总不会死循环吧?
//        val key = prop.keys().nextElement().asInstanceOf[String]
//        val value = prop.elements().nextElement().asInstanceOf[String]
//        println(key, value)
//      }
      conf.setMaster("coding.local:7077").setAppName("test")
    } catch {
      case e: Throwable => e.printStackTrace()
    }
    conf
  }

}