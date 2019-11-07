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

val scala212 = "2.12.6"
//val scala212 = "2.11.12"

scalaVersion := scala212
val akkaVersion = "2.5.12"

val main = Project(id = "EhTail", base = file("."))

resolvers += Resolver.jcenterRepo // for redis

libraryDependencies ++=
  Seq(
    "tech.navicore" %% "akkaeventhubs" % "0.9.3",

    "ch.qos.logback" % "logback-classic" % "1.1.7",
    "com.typesafe" % "config" % "1.3.4",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",

    //"tech.navicore" %% "navipath" % "0.1.6",

    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,

    "com.github.scullxbones" %% "akka-persistence-mongo-casbah" % "2.0.4",
    "org.mongodb" %% "casbah-core" % "3.1.1",

    "com.hootsuite" %% "akka-persistence-redis" % "0.7.0",

    "org.json4s" %% "json4s-native" % "3.6.1",

    "org.rogach" %% "scallop" % "3.1.3",

    "org.scalatest" %% "scalatest" % "3.0.1" % "test"

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

