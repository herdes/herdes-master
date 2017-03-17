organization := "io.herdes"
name := "herdes-master"
version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val akkaV = "2.4.16"
  val akkaHttpV = "10.0.3"
  val scalazVersion = "7.1.9"
  Seq(
    "com.typesafe.akka"   %% "akka-actor"    % akkaV,
    "com.typesafe.akka"   %% "akka-multi-node-testkit" % "2.4.2",
    "com.typesafe.akka"   %% "akka-http-core" % akkaHttpV,
    "com.typesafe.akka"   %% "akka-stream" % akkaV,
    "com.typesafe.akka"   %% "akka-remote" % akkaV,
    "com.typesafe.akka"   % "akka-http-spray-json-experimental_2.11" % "2.4.11",
    "net.liftweb"         %% "lift-json" % "2.6",
    "com.typesafe.akka"   %% "akka-testkit"  % akkaV,
    "com.typesafe.akka"   %% "akka-http-testkit"  % akkaHttpV,
    "io.herdes.shared"    %% "herdes-commons" % "1.0",
    "io.herdes.shared"    %% "herdes-orient" % "1.0",
    "io.scalaberries"     %% "berries" % "1.0",
    "ch.megard"           %% "akka-http-cors" % "0.1.4",
    "com.chuusai"         %% "shapeless" % "2.3.2",
    "org.scalaz"          %% "scalaz-core" % scalazVersion,
    "org.scalaz"          %% "scalaz-effect" % scalazVersion,
    "org.scalaz"          %% "scalaz-typelevel" % scalazVersion,
    "org.scalaz"          %% "scalaz-scalacheck-binding" % scalazVersion % "test",
    "org.scalatest"       %% "scalatest" % "2.2.6" % "test",
    "org.mockito"         % "mockito-core" % "1.10.19"
  )
}

fork := true
scalacOptions ++= Seq("-Xmax-classfile-name", "110")