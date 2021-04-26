name := "scala-course"

version := "0.1"

scalaVersion := "2.13.5"

val http4sVersion = "0.21.21"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.6.0",
  "org.typelevel" %% "cats-effect" % "2.5.0" withSources() withJavadoc(),
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  // Optional for auto-derivation of JSON codecs
  "io.circe" %% "circe-generic" % "0.13.0",
  // Optional for string interpolation to JSON model
  "io.circe" %% "circe-literal" % "0.13.0"
)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds")