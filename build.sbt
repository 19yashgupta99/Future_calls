name := "OOPs_Testing_Assignment"

version := "0.1"

scalaVersion := "2.13.5"

coverageExcludedPackages := ".*bootstrap.*"

libraryDependencies ++= Seq (
  "org.scalatest" %% "scalatest" % "3.2.3" % Test,
  "org.mockito" % "mockito-core" % "3.6.28" % Test
)