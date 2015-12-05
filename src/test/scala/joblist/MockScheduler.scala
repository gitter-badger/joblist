package joblist

import better.files.File

/**
  * A mock implementation of a scheduler
  *
  * @author Holger Brandl
  */
class MockScheduler extends JobScheduler {

  var configs: List[JobConfiguration] = List()
  var queue: List[QueueStatus] = List()


  override def readIdsFromStdin(): List[Int] = ???


  override def getQueued: List[QueueStatus] = queue


  override def updateRunInfo(id: Int, runinfoFile: File): Unit = ???


  override def submit(jc: JobConfiguration): Int = ???
}