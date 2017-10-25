package test.codebabe.server.mqc.spark

import me.codebabe.engine.spark.SparkEngine

import scala.math.random

/**
  * author: code.babe
  * date: 2017-10-09 20:03
  */
object SparkPi {
  def main(args: Array[String]): Unit = {
    val context = SparkEngine.getEngine()

    val slices = 10
    val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
    val count = context.parallelize(1 until n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x*x + y*y <= 1) 1 else 0
    }.reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / (n - 1))
    context.stop()
  }
}
