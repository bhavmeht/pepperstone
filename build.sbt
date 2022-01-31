import sbt._

name := "pepperstone"
version := "0.1"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(name := "pepperstone")
  .settings(
    inThisBuild(
      Seq(
        organization := "com.pepperstone.challenge",
        scalaVersion := "2.12.12",
        scalacOptions ++= Seq(
          "-feature",
          "-deprecation",
          "-unchecked",
          "-Xcheckinit",
          "-Xlint",
          "-Xverify",
          "-Yno-adapted-args",
          "-encoding", "utf8"
        ),
        Test / coverageEnabled := true,
        Compile / coverageEnabled := false,
        Compile / mainClass := Some("com.pepperstone.challenge.ScrambledWordAnalysis")
      )
    )
  )
  .settings(Test / parallelExecution := false)
  .settings(
    libraryDependencies ++= (Dependencies.compile ++ Dependencies.test)
  )
  .settings(
    // Assembly settings
    buildInfoPackage := "com.pepperstone.challenge",
    assembly / test  := {},
    assembly / assemblyJarName  := name.value + "-assembly.jar"
  )
