package me.codebabe.common.calculate

import java.util.concurrent.{Callable, ExecutorService, Executors, Future}

import scala.collection.mutable

/**
  * author: code.babe
  * date: 2017-08-17 13:29
  */
abstract class AbsConcurrentCalculator[T](pool: ExecutorService, tasks: mutable.ListBuffer[Future[T]]) extends ConcurrentCalculator[T] {

  def this(pool: ExecutorService) = {
    this(pool, new mutable.ListBuffer[Future[T]]())
  }

  def this() = {
    this(Executors.newFixedThreadPool(Runtime.getRuntime.availableProcessors * 8), new mutable.ListBuffer[Future[T]]())
  }

  /**
    * 添加执行任务, 这里使用隐式方法
    *
    * @param calculate 具体怎么计算
    */
  def addTask(implicit calculate: () => T): Unit = {
    tasks +=
      pool.submit(new Callable[T] {
        override def call(): T = {
          calculate()
        }
      })
  }

  def getTasks: mutable.ListBuffer[Future[T]] = {
    tasks
  }

  def getPool: ExecutorService = {
    pool
  }

}
