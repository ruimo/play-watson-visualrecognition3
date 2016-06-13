name := """play-watson-visual-recognition-module"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "ruimo.com" at "http://static.ruimo.com/release"

libraryDependencies ++= Seq(
  ws,
  "com.ruimo" %% "scoins" % "1.4",
  "org.specs2" %% "specs2-core" % "3.8.2" % Test,
  "org.specs2" %% "specs2-junit" % "3.8.2" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
