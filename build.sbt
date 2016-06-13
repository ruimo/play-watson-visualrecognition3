name := """play-watson-visual-recognition-module"""

organization := "com.ruimo"

version := "1.0-SNAPSHOT"

publishTo := Some(
  Resolver.file(
    "play-watson-visual-recognition-module",
    new File(Option(System.getenv("RELEASE_DIR")).getOrElse("/tmp"))
  )
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "ruimo.com" at "http://static.ruimo.com/release"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  ws,
  "com.ruimo" %% "scoins" % "1.4",
  "org.specs2" %% "specs2-core" % "3.8.2" % Test,
  "org.specs2" %% "specs2-junit" % "3.8.2" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
