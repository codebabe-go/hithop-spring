package test.codebabe.common

/**
  * author: code.babe
  * date: 2017-09-22 14:47
  */
object CommonScalaTest extends App {
  val list = List(
    ("a", "aa", 1),
    ("a", "aa", 2),
    ("a", "ab", 1),
    ("b", "aa", 1),
    ("b", "bb", 1)
  )
  val ret = list.groupBy(l => (l._1, l._2)).foreach{
    case (_, l) => {
      l.foreach(ll => {
        println(ll.toString())
      })
    }
  }

  val intList = List[Int](
    1,
    2,
    3
  )
  val strList = intList.asInstanceOf[List[String]]
  println(intList)
  println(strList)
}
