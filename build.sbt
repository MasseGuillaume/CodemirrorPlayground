import ScalaJSHelper._
import org.scalajs.sbtplugin.JSModuleID

lazy val baseSettings = Seq(
  scalaVersion := "2.11.8",
  scalacOptions := Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yinline-warnings",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused-import",
    "-Ywarn-value-discard"
  )
)

lazy val root = project.in(file("."))
  .settings(baseSettings)
  .aggregate(codemirror, client)
  .dependsOn(codemirror, client)

def codemirrorD(path: String): JSModuleID =
  "org.webjars.bower"  % "codemirror" % "5.18.2" % "compile" / s"$path.js" minified s"$path.js"

def react(artifact: String, name: String): JSModuleID = 
  "org.webjars.bower" % "react" % "15.2.1" % "compile" / s"$artifact.js" minified s"$artifact.min.js" commonJSName name

def react(artifact: String, name: String, depends: String): JSModuleID =
  react(artifact, name).dependsOn(s"$depends.js")

lazy val codemirror = project
  .settings(baseSettings)
  .settings(
    jsDependencies ++=
      List(
        "lib/codemirror",
        "addon/comment/comment",
        "addon/dialog/dialog",
        "addon/edit/closebrackets",
        "addon/edit/matchbrackets",
        "addon/fold/brace-fold",
        "addon/fold/foldcode",
        "addon/hint/show-hint",
        "addon/runmode/runmode",
        "addon/scroll/scrollpastend",
        "addon/scroll/simplescrollbars",
        "addon/search/match-highlighter",
        "addon/search/search",
        "addon/search/searchcursor",
        "keymap/sublime",
        "mode/clike/clike"
      ).map(codemirrorD),
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"
  )
  .enablePlugins(ScalaJSPlugin)

lazy val client = project
  .settings(baseSettings)
  .settings(
    JsEngineKeys.engineType := JsEngineKeys.EngineType.Node,
    skip in packageJSDependencies := false,
    jsDependencies ++= Seq(
      react("react-with-addons", "React"),
      react("react-dom", "ReactDOM", "react-with-addons"),
      react("react-dom-server", "ReactDOMServer", "react-dom")
    ),
    libraryDependencies += "com.github.japgolly.scalajs-react" %%% "extra" % "0.11.1"
  )
  .enablePlugins(ScalaJSPlugin, SbtWeb)
  .dependsOn(codemirror)

lazy val web = project
  .settings(baseSettings)
  .settings(packageScalaJS(client))
  .settings(
    products in Compile <<= (products in Compile).dependsOn(WebKeys.assets in Assets),
    reStart <<= reStart.dependsOn(WebKeys.assets in Assets),
    unmanagedResourceDirectories in Compile += (WebKeys.public in Assets).value,
    libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "2.4.9"
  )
  .dependsOn(client)
  .enablePlugins(SbtWeb)