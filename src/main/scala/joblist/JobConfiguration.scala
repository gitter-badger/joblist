package joblist

import better.files.File

/**
  * Defines a job and how it should be run
  *
  * @author Holger Brandl
  */

case class JobConfiguration(cmd: String, name: String = "", wallTime: String = "", queue: String = "short", numThreads: Int = 1, otherQueueArgs: String = "", wd: File = File(".")) {

  def saveAsXml(jobId: Int, inDir: File) = {
    val xmlFile = JobConfiguration.jcXML(jobId, inDir)
    toXml(this, xmlFile)
  }


  /** If the job configuration does not come along with a name we create one is unique. */
  def withName() = {
    if (name == null || name.isEmpty) {
      this.copy(name = buildJobName(wd, cmd))
    } else {
      this
    }
  }


  // we don't use val here to avoid that it's serialized into the xml
  def logs = new JobLogs(name, wd)
}


/** Log files that might be of interest for the user. JL does not rely on them but tries to create in a consistent
  * manner them irrespective of the used scheduler. */
case class JobLogs(name: String, wd: File) {

  def logsDir = wd / s".logs"


  // file getters
  val id = logsDir / s"$name.jobid"
  val cmd = logsDir / s"$name.cmd"
  val err = logsDir / s"$name.err.log"
  val out = logsDir / s"$name.out.log"
}

// companion object method for JC
object JobConfiguration {

  def jcXML(jobId: Int, inDir: File = File(".")): File = {
    inDir.createIfNotExists(true) / s"$jobId.job"
  }


  def fromXML(jobId: Int, wd: File = File(".")): JobConfiguration = {
    val xmlFile = jcXML(jobId, wd)
    fromXml(xmlFile).asInstanceOf[JobConfiguration]
  }
}
