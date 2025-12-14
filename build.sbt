
ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "2.12.21"

lazy val root = (project in file("."))
  .settings(
    name := "scala-spark-demo",
    idePackagePrefix := Some("com.github.aebel"),
    // Add Spark Dependencies
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "3.5.3" % "provided",
      "org.apache.spark" %% "spark-sql" % "3.5.3" % "provided",
      "com.typesafe" % "config" % "1.4.5"
    )
  )

