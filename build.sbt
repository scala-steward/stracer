import Dependencies._

ThisBuild / versionScheme := Some("semver-spec")

lazy val modules: List[ProjectReference] = List(
  `stracer`,
  `stracer-play-json`,
  `stracer-circe`,
  `stracer-kafka`
)

lazy val root = project
  .in(file("."))
  .settings(name := "stracer")
  .settings(commonSettings)
  .settings(alias)
  .settings(publish / skip := true)
  .aggregate(modules: _*)

lazy val `stracer` = project
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      zipkin,
      random,
      Scodec.core,
      Scodec.bits,
      `cats-helper`,
      Cats.core,
      CatsEffect.effect,
      configTools,
      Pureconfig.pureconfig,
      scalatest % Test))

lazy val `stracer-play-json` = project
  .dependsOn(`stracer`)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      PlayJsonTools.tools,
      scalatest % Test))

lazy val `stracer-circe` = project
  .dependsOn(`stracer`)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Circe.all ++ Seq(scalatest % Test))

lazy val `stracer-kafka` = project
  .dependsOn(`stracer`)
  .settings(commonSettings)
  .settings(libraryDependencies ++= Seq(skafka, scalatest % Test))

val commonSettings = Seq(
  description := "Library for distributed tracing in Scala",
  organization := "com.evolutiongaming",
  homepage := Some(url("https://github.com/evolution-gaming/stracer")),
  startYear := Some(2019),
  organizationName := "Evolution",
  organizationHomepage := Some(url("https://evolution.com")),
  scalaVersion := crossScalaVersions.value.head,
  crossScalaVersions := Seq("2.13.15", "2.12.20"),
  releaseCrossBuild := true,
  licenses := Seq(("MIT", url("https://opensource.org/licenses/MIT"))),
  publishTo := Some(Resolver.evolutionReleases),
  scalacOptsFailOnWarn := Some(false))

val alias: Seq[sbt.Def.Setting[?]] =
  //  addCommandAlias("check", "all versionPolicyCheck Compile/doc") ++
  addCommandAlias("check", "show version") ++
    addCommandAlias("build", "+all compile test")

