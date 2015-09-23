lazy val commonSettings = Seq(
  organization := "com.github",
  version := "0.1.0",
  scalaVersion := "2.11.7"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "RockPaperScissors"
   )
