name := "cat-api"

version := "0.1"

scalaVersion := "2.12.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  val akkaVersion = "2.4.10"
  val slickVersion = "3.1.1"
  Seq(
    "com.typesafe.akka" %% "akka-http-experimental" % akkaVersion,
//    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaVersion,
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" % "slick-hikaricp_2.11" % slickVersion,
    "mysql" % "mysql-connector-java" % "8.0.11",
    "org.slf4j" % "slf4j-nop" % "1.6.4"
  )
}
