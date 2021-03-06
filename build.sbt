import sbt.Resolver

name := "joblist"
organization := "de.mpicbg.scicomp"

version := "0.4-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += Resolver.mavenLocal


//libraryDependencies += "com.github.pathikrit" %% "better-files" % "2.13.0"
//libraryDependencies += "com.github.pathikrit" %% "better-files" % "2.13.0-SNAPSHOT"


// just needed for the unit-tests
libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

// needed for cli interface
libraryDependencies += "org.docopt" % "docopt" % "0.6.0-SNAPSHOT"

// common utilities
libraryDependencies += "de.mpicbg.scicomp" % "scalautils_2.11" % "0.1-SNAPSHOT"


// add xstream without sub-dependencies because they are optional and contain class-duplicates in pull-parser
// see http://x-stream.github.io/faq.html
// http://x-stream.github.io/http://stackoverflow.com/questions/15560598/play-2-0-sbt-exclude-certain-transitive-dependencies-from-some-all-modules-in
libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.8" intransitive()


libraryDependencies ++= Seq(
  "org.joda" % "joda-convert" % "1.5",
  "joda-time" % "joda-time" % "2.3"
)

// todo remove for release or use custom property
//libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.4.8" % "test" cross CrossVersion.full
//sys.env.get("TERM_PROGRAM").isDefined match {
//  case true => libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.4.9-SNAPSHOT" % "test" cross CrossVersion.full
//  case false => libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.4.8" % "test" cross CrossVersion.full
//}

sys.env.get("USE_AMMO").isDefined match {
  case true => {
    println("using ammonite shell")
    libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.5.0" % "test" cross CrossVersion.full
    // note does not work because the wole sb expression can just do ONE thing and not two. Why?
    //    initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""
  }
  case false => initialCommands in(Test, console) := """"""
}


initialCommands in(Test, console) := """ammonite.repl.Repl.run("")"""


// disable tests
// see http://stackoverflow.com/questions/9763543/how-can-i-skip-tests-in-an-sbt-build
//test in assembly := {joblist.JobListCLI.class}
test in assembly := {}

// to test just some follow http://stackoverflow.com/questions/6997730/how-to-execute-tests-that-match-a-regular-expression-only
//sbt> testOnly com.example.*Spec


//http://www.scala-sbt.org/0.12.4/docs/Detailed-Topics/Testing.html
parallelExecution in Test := false