name := """play-watson-visual-recognition-module"""

organization := "com.ruimo"

crossScalaVersions := List("2.11.8", "2.12.8") 

scalaVersion := "2.12.3"

publishTo := Some(
  Resolver.file(
    "play-watson-visual-recognition-module",
    new File(Option(System.getenv("RELEASE_DIR")).getOrElse("/tmp"))
  )
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "ruimo.com" at "http://static.ruimo.com/release"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  ws,
  "com.ruimo" %% "scoins" % "1.22",
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
