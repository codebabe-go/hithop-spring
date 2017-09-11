package me.codebabe.common.calculate

/**
  * author: code.babe
  * date: 2017-08-17 10:49
  */
trait ConcurrentCalculator[T] {

  /**
    * 求和
    *
    * @return
    */
  def summary(): T

  /**
    * 求差
    *
    * @return
    */
  def deviation(): T

  /**
    * 求乘积
    *
    * @return
    */
  def product(): T

  /**
    * 求商
    *
    * @return
    */
  def consult(): T

}
