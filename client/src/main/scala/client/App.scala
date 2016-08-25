package client

import japgolly.scalajs.react._, vdom.all._

object App {
  case class Annotation(
    row: Int,
    column: Int,
    text: String,
    `type`: String
  )

  case class State(
    code: String,
    dark: Boolean = false,
    annotations: List[Annotation] = Nil,
    sideBarClosed: Boolean = false) {

    def toogleTheme = copy(dark = !dark)
    def toogleSidebar = copy(sideBarClosed = !sideBarClosed)
  }

  class Backend(scope: BackendScope[_, State]) {
    def toogleTheme(e: ReactEventI)   = scope.modState(_.toogleTheme)
    def toogleSidebar(e: ReactEventI) = scope.modState(_.toogleSidebar)
    def templateOne(e: ReactEventI)   = scope.modState(_.copy(code = "code 1"))
    def templateTwo(e: ReactEventI)   = scope.modState(_.copy(code = "code 2"))
    def templateThree(e: ReactEventI) = scope.modState(_.copy(code = "code 3"))
    def error(e: ReactEventI)         = scope.modState(_.copy(annotations = 
      List(
        Annotation(row = 1, column = 0, text = "Unused import", `type` = "error")
      )
    ))
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
        li(button(onClick ==> backend.error)("error"))
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
        div(`class` := s"editor $sideStyle")(Editor(state)),
        div(`class` := s"sidebar $sideStyle")(SideBar((state, scope.backend)))
      )
    })
    .build

  def apply() = component()
}