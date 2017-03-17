name := """play-watson-visual-recognition-module"""

organization := "com.ruimo"

publishTo := Some(
  Resolver.file(
    "play-watson-visual-recognition-module",
    new File(Option(System.getenv("RELEASE_DIR")).getOrElse("/tmp"))
  )
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

resolvers += "ruimo.com" at "http://static.ruimo.com/release"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq(
  ws,
  "com.ruimo" %% "scoins" % "1.9",
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
