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

val scala212 = "2.13.10"

scalaVersion := scala212

val akkaVersion = "2.6.20"

val main = Project(id = "EhTail", base = file("."))

resolvers += Resolver.jcenterRepo // for redis

libraryDependencies ++=
  Seq(

    "tech.navicore" %% "akkaeventhubs" % "1.6.3",

    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",

    "com.typesafe" % "config" % "1.4.2",

    "ch.qos.logback" % "logback-classic" % "1.4.4",

    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,

    "org.json4s" %% "json4s-native" % "4.0.6",

    "org.rogach" %% "scallop" % "4.1.0",

    "org.scalatest" %% "scalatest" % "3.2.14" % "test"

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

