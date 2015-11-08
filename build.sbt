lazy val root = project.in(file(".")).enablePlugins(ScalaJSPlugin)

name := "A web-based mini IDE for writing latex equations"

normalizedName := "math-ide"

version := "0.1"

organization := "vu.co.kaiyin"

scalaVersion := "2.11.7"


crossScalaVersions := Seq("2.10.4", "2.11.7")

scalacOptions ++= Seq("-feature", "-deprecation")

skip in packageJSDependencies := false

//resolvers += "amateras-repo" at "http://amateras.sourceforge.jp/mvn-snapshot/"
//
//libraryDependencies += "com.scalawarrior" %%% "scalajs-ace" % "0.0.1-SNAPSHOT"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.kindlychung" % "sjs-katex" % "0.1"

libraryDependencies += "com.lihaoyi" %%% "scalarx" % "0.2.8"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0"
)

jsDependencies in Test += RuntimeDOM

homepage := Some(url("https://github.com/kindlychung/math-ide"))

