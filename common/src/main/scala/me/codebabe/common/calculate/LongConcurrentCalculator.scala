package me.codebabe.common.calculate

import java.util.concurrent.ExecutorService
import java.util.concurrent.atomic.AtomicLong

/**
  * author: code.babe
  * date: 2017-08-17 11:20
  */
class LongConcurrentCalculator(pool: ExecutorService) extends AbsConcurrentCalculator[Long](pool) {

  /**
    * 求和
    *
    * @return
    */
  override def summary(): Long = {
    val ret = new AtomicLong(0)
    for (task <- getTasks) {
      ret.addAndGet(task.get())
    }
    ret.get()
  }

  /**
    * 求差
    *
    * @return
    */
  override def deviation(): Long = ???

  /**
    * 求乘积
    *
    * @return
    */
  override def product(): Long = ???

  /**
    * 求商
    *
    * @return
    */
  override def consult(): Long = ???
}