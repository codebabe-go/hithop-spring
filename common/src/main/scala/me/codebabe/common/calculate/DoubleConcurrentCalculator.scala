package me.codebabe.common.calculate

import java.util.concurrent.ExecutorService

import com.google.common.util.concurrent.AtomicDouble

/**
  * author: code.babe
  * date: 2017-08-17 11:31
  */
class DoubleConcurrentCalculator(pool: ExecutorService) extends AbsConcurrentCalculator[Double] {

  /**
    * 求和
    *
    * @return
    */
  override def summary(): Double = {
    val ret = new AtomicDouble()
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
  override def deviation(): Double = ???

  /**
    * 求乘积
    *
    * @return
    */
  override def product(): Double = ???

  /**
    * 求商
    *
    * @return
    */
  override def consult(): Double = ???
}
