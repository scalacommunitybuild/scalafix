import sbt._
/* scalafmt: {
maxColumn = 120
style = defaultWithAlign
}*/

object Build {
  val scalametaV = "1.6.0-641"
  val ammoniteV  = "0.8.2"
  val scalatestV = "3.0.0"
}

object Dependencies {
  import Build._
  def scalahost(scalaVersion: String): ModuleID = "org.scalameta" % s"scalahost_$scalaVersion" % scalametaV
  def scalameta: ModuleID                       = "org.scalameta" %% "contrib"                 % scalametaV
  def scalatest: ModuleID                       = "org.scalatest" %% "scalatest"               % scalatestV
}
