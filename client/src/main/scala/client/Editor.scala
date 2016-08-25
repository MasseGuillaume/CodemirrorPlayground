package client

import japgolly.scalajs.react._, vdom.all._

import scalacss.Defaults._
import scalacss.ScalaCssReact._

import org.scalajs.dom.raw.Element
import org.scalajs.dom

import scala.scalajs._

object Editor {
  object Style extends StyleSheet.Inline {
    import dsl._

    val code = style(
      height(100.%%),
      margin.`0`
    )
  }

  private[Editor] case class EditorState(editor: Option[codemirror.Editor] = None)
  private[Editor] class Backend(scope: BackendScope[App.State, EditorState]) {
    private def mount(el: Element) = {
      scope.props.map{props =>
        val isMac = dom.window.navigator.userAgent.contains("Mac")
        val ctrl = if(isMac) "Cmd" else "Ctrl"

        codemirror.CodeMirror(
          el,
          js.Dictionary(
            "value" -> props.code,
            "mode" -> "text/x-scala",
            "autofocus" -> true,
            "lineNumbers" -> false,
            "lineWrapping" -> false,
            "tabSize" -> 2,
            "indentWithTabs" -> false,
            "theme" -> "solarized dark",
            "smartIndent" -> true,
            "keyMap" -> "sublime",
            "scrollPastEnd" -> true,
            "scrollbarStyle" -> "simple",
            "extraKeys" -> js.Dictionary(
              "Tab"          -> "specialTab",
              s"$ctrl-l"     -> null,
              s"$ctrl-Space" -> "autocomplete",
               "."           -> "autocompleteDot",
              s"$ctrl-."     -> "typeAt",
              s"$ctrl-Enter" -> "run",
              "F1"           -> "help",
              "F2"           -> "solarizedToggle",
              "F7"           -> "share"
            ),
            "autoCloseBrackets" -> true,
            "matchBrackets" -> true,
            "showCursorWhenSelecting" -> true,
            "autofocus" -> true,
            "highlightSelectionMatches" -> js.Dictionary(
              "showToken" -> js.Dynamic.global.RegExp("\\w")
            )
          ).asInstanceOf[codemirror.Options]
        )
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
    .render( _ => pre(Style.code))
    .componentWillReceiveProps(v => CallbackTo[Unit]{
      val current = v.currentProps
      val next = v.nextProps
      val state = v.currentState

      if(current.dark != next.dark) {
        val label = 
          if(next.dark) "dark"
          else "light"
        // state.editor.foreach(_.setTheme(s"ace/theme/solarized_$label"))
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