import sbt._
import Keys._
import play.Play.autoImport._
import com.typesafe.sbt.SbtNativePackager.autoImport._
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import PlayKeys._
import play.PlayScala
import play.sbt.routes.RoutesKeys._

object BuildSettings {
  import Dependencies._
  import Resolvers._

  val buildOrganization = "com.leoromanovsky"
  val buildVersion = "1.0"
  val buildScalaVersion = "2.11.4"

  val globalSettings = Seq(
    organization := buildOrganization,
    version := buildVersion,
    scalaVersion := buildScalaVersion,
    scalacOptions += "-deprecation",
    fork in test := true,
    libraryDependencies ++= Seq(),
    resolvers := Seq(
      scalaToolsRepo,
      jbossRepo,
      sonatypeRepo,
      typeSafeRepo,
      scalazRepo,
      atlassianRepo))

  val projectSettings = globalSettings
}

object Resolvers {
  val sonatypeRepo = "Sonatype Release" at "http://oss.sonatype.org/content/repositories/releases"
  val scalaToolsRepo = "Scala Tools" at "http://scala-tools.org/repo-snapshots/"
  val jbossRepo = "JBoss" at "http://repository.jboss.org/nexus/content/groups/public/"
  val typeSafeRepo = "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"
  val scalazRepo = "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
  val atlassianRepo = "Atlassian Releases" at "https://maven.atlassian.com/public/"
}

object Dependencies {
  val slick = "com.typesafe.slick" %% "slick" % "3.0.0"
  val slf4j = "org.slf4j" % "slf4j-nop" % "1.6.4"
  val playSlick = "com.typesafe.play" %% "play-slick" % "1.0.0"
  val mysql = "mysql" % "mysql-connector-java" % "5.1.35"
  val spec2 = specs2 % Test
  val scalatest = "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
}

object Build extends Build {
  import BuildSettings._
  import Dependencies._
  import Resolvers._

  override lazy val settings = super.settings ++ globalSettings

  lazy val web = Project("web",
    file("."),
    settings = projectSettings ++
      Seq(
        libraryDependencies ++= Seq(
          spec2,
          scalatest,
          ws,
          mysql,
          slick,
          "com.typesafe.play" %% "play-slick-evolutions" % "1.0.0",
          slf4j,
          playSlick),
        javaOptions in Test += "-Dconfig.file=conf/test.conf",
        routesGenerator := InjectedRoutesGenerator
      )
    )
    .enablePlugins(PlayScala, JavaAppPackaging)
}
