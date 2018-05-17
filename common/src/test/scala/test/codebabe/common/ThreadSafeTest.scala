package test.codebabe.common

import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.{LinkedBlockingQueue, ThreadFactory, ThreadPoolExecutor, TimeUnit}

import com.google.common.collect.Lists

import scala.collection.JavaConverters._
import scala.collection.parallel.ThreadPoolTaskSupport

/**
  * author: code.babe
  * date: 2018-03-16 14:03
  */
object ThreadSafeTest extends App {

  val atomicLong = new AtomicLong(0L)

  val list = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13).asScala.toParArray
  list.tasksupport = {
    val numCores = Runtime.getRuntime.availableProcessors

    val tcount = new java.util.concurrent.atomic.AtomicLong(0L)

    val defaultThreadPool = new ThreadPoolExecutor(
      numCores * 16,
      numCores * 64,
      60L, TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue[Runnable](1024 * 8),
      new ThreadFactory {
        def newThread(r: Runnable) = {
          val t = new Thread(r)
          t.setName("list-polling-thread-" + tcount.incrementAndGet)
          t.setDaemon(true)
          t
        }
      },
      new ThreadPoolExecutor.CallerRunsPolicy
    )
    new ThreadPoolTaskSupport(defaultThreadPool)
  }


  list.foreach(_ => {
    atomicLong.addAndGet(1)
  })

  println(atomicLong.get())

}
