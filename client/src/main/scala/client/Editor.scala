package client

import japgolly.scalajs.react._, vdom.all._

import org.scalajs.dom.raw.{Element, HTMLTextAreaElement}
import org.scalajs.dom

import scala.scalajs._

object Editor {

  private def options(code: String): codemirror.Options = {
    val isMac = dom.window.navigator.userAgent.contains("Mac")
    val ctrl = if(isMac) "Cmd" else "Ctrl"
  
    js.Dictionary[Any](
      "value"                     -> code,
      "mode"                      -> "text/x-scala",
      "autofocus"                 -> true,
      "lineNumbers"               -> false,
      "lineWrapping"              -> false,
      "tabSize"                   -> 2,
      "indentWithTabs"            -> false,
      "theme"                     -> "solarized light",
      "smartIndent"               -> true,
      "keyMap"                    -> "sublime",
      "scrollPastEnd"             -> true,
      "scrollbarStyle"            -> "simple",
      "autoCloseBrackets"         -> true,
      "matchBrackets"             -> true,
      "showCursorWhenSelecting"   -> true,
      "autofocus"                 -> true,
      "highlightSelectionMatches" -> js.Dictionary("showToken" -> js.Dynamic.global.RegExp("\\w")),
      "extraKeys"                 -> js.Dictionary(
        "Tab"          -> "specialTab",
        s"$ctrl-l"     -> null,
        s"$ctrl-Space" -> "autocomplete",
         "."           -> "autocompleteDot",
        s"$ctrl-."     -> "typeAt",
        s"$ctrl-Enter" -> "run",
        "F1"           -> "help",
        "F2"           -> "solarizedToggle",
        "F7"           -> "share"
      )
    ).asInstanceOf[codemirror.Options]
  }

  private[Editor] case class EditorState(editor: Option[codemirror.Editor] = None)
  private[Editor] class Backend(scope: BackendScope[App.State, EditorState]) {
    private def mount(el: Element) = {
      scope.props.map{props =>
        el match {
          case e: HTMLTextAreaElement => codemirror.CodeMirror.fromTextArea(e, options(props.code))
          case _ => ???
        }
      }
    }

    def start = 
      mount(scope.getDOMNode).flatMap(e =>
        scope.modState(_.copy(editor = Some(e)))
      )
  }

  val component = ReactComponentB[App.State]("AceEditor")
    .initialState(EditorState())
    .backend(new Backend(_))
    .render( _ => textarea())
    .componentWillReceiveProps(v => CallbackTo[Unit]{
      val current = v.currentProps
      val next = v.nextProps
      val state = v.currentState

      if(current.dark != next.dark) {
        val theme = 
          if(next.dark) "dark"
          else "light"

        state.editor.foreach(_.setOption("theme", s"solarized $theme"))
      }

      if(current.code != next.code) {
        state.editor.foreach(_.getDoc().setValue(next.code))
      }

      ()
    })
    .componentDidMount(_.backend.start)
    .build
 
  def apply(state: App.State) = component(state)
}