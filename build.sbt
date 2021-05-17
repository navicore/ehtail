name := "EhTail"
organization := "tech.navicore"

fork := true
javaOptions in test ++= Seq(
  "-Xms512M", "-Xmx2048M",
  "-XX:MaxPermSize=2048M",
  "-XX:+CMSClassUnloadingEnabled"
)

parallelExecution in test := false

version := "0.1.0"

val scala212 = "2.13.6"

scalaVersion := scala212

val akkaVersion = "2.6.14"

val main = Project(id = "EhTail", base = file("."))

resolvers += Resolver.jcenterRepo // for redis

libraryDependencies ++=
  Seq(

    "tech.navicore" %% "akkaeventhubs" % "1.5.1",

    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",

    "com.typesafe" % "config" % "1.4.1",

    "ch.qos.logback" % "logback-classic" % "1.2.3",

    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,

    "org.json4s" %% "json4s-native" % "3.6.11",

    "org.rogach" %% "scallop" % "4.0.2",

    "org.scalatest" %% "scalatest" % "3.2.9" % "test"

  )

dependencyOverrides ++= Seq(
  "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion
)

mainClass in assembly := Some("onextent.akka.azure.ehtail.Cli")
assemblyJarName in assembly := "EhTail.jar"

assemblyMergeStrategy in assembly := {
  case PathList("reference.conf") => MergeStrategy.concat
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("META-INF", _ @ _*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

