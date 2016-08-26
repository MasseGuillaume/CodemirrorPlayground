package client

import japgolly.scalajs.react._, vdom.all._

sealed trait Severity
final case object Info extends Severity
final case object Warning extends Severity
final case object Error extends Severity

case class Position(start: Int, end: Int)

case class CompilationInfo(
  severity: Severity,
  position: Position,
  message: String
)

object App {
  case class State(
    code: String,
    dark: Boolean = false,
    compilationInfos: Set[CompilationInfo] = Set(),
    sideBarClosed: Boolean = false) {

    def toogleTheme = copy(dark = !dark)
    def toogleSidebar = copy(sideBarClosed = !sideBarClosed)
  }

  class Backend(scope: BackendScope[_, State]) {
    def codeChange(newCode: String)      = scope.modState(_.copy(code = newCode))
    def toogleTheme(e: ReactEventI)   = scope.modState(_.toogleTheme)
    def toogleSidebar(e: ReactEventI) = scope.modState(_.toogleSidebar)
    def templateOne(e: ReactEventI)   = scope.modState(_.copy(code = "code 1"))
    def templateTwo(e: ReactEventI)   = scope.modState(_.copy(code = "code 2"))
    def templateThree(e: ReactEventI) = scope.modState(_.copy(code = "code 3"))
  }

  val SideBar = ReactComponentB[(State, Backend)]("SideBar")
    .render_P { case (state, backend) =>
      val label = if(state.dark) "light" else "dark"
      ul(
        li(button(onClick ==> backend.toogleSidebar)("X")),
        li(button(onClick ==> backend.toogleTheme)(label)),
        li(button(onClick ==> backend.templateOne)("template 1")),
        li(button(onClick ==> backend.templateTwo)("template 2")),
        li(button(onClick ==> backend.templateThree)("template 3")),
        li(pre(state.code))
      )
    }
    .build

  val defaultCode = 
    """
import akka.http.scaladsl._
import server.Directives._

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.duration._
import scala.concurrent.Await

object Main {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("server")
    import system.dispatcher
    implicit val materializer = ActorMaterializer()

    val route = {
      path("assets" / Remaining) { path ⇒
        getFromResource(path)
      } ~
      pathSingleSlash {
        getFromResource("index.html")
      }
    }

    Await.result(Http().bindAndHandle(route, "localhost", 8080), 20.seconds)

    ()
  }
}
    """

  val component = ReactComponentB[Unit]("App")
    .initialState(State(code = defaultCode))
    .backend(new Backend(_))
    .renderPS((scope, _, state) => {
      val sideStyle = 
        if(state.sideBarClosed) "sidebar-closed"
        else "sidebar-open"

      div(`class` := "app")(
        div(`class` := s"editor $sideStyle")(Editor(state, scope.backend)),
        div(`class` := s"sidebar $sideStyle")(SideBar((state, scope.backend)))
      )
    })
    .build

  def apply() = component()
}