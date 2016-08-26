package client

import japgolly.scalajs.react._, vdom.all._

import org.scalajs.dom.raw.{Element, HTMLTextAreaElement}
import org.scalajs.dom

import codemirror.{TextAreaEditor => CodeMirror, LineWidget}

import scala.scalajs._

object Editor {

  val codemirrorTextarea = Ref[HTMLTextAreaElement]("codemirror-textarea")

  private def options(dark: Boolean): codemirror.Options = {

    val theme = if(dark) "dark" else "light"

    dom.console.log(code)

    val isMac = dom.window.navigator.userAgent.contains("Mac")
    val ctrl = if(isMac) "Cmd" else "Ctrl"
  
    js.Dictionary[Any](
      "mode"                      -> "text/x-scala",
      "autofocus"                 -> true,
      "lineNumbers"               -> false,
      "lineWrapping"              -> false,
      "tabSize"                   -> 2,
      "indentWithTabs"            -> false,
      "theme"                     -> s"solarized $theme",
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


  private[Editor] sealed trait Annotation { 
    def clear(): Unit 
  }

  private[Editor] case class Line(lw: LineWidget) extends Annotation {
    def clear() = lw.clear()
  }

  // private[Editor] case class Marked(tm: TextMarker) extends Annotation {
  //   def clear() = tm.clear()
  // }

  private[Editor] case class EditorState(
    editor: Option[CodeMirror] = None,
    annotations: Map[CompilationInfo, Annotation] = Map()
  )

  private[Editor] class Backend(scope: BackendScope[(App.State, App.Backend), EditorState]) {
    def stop() = {
      scope.modState{s =>
        s.editor.map(_.toTextArea())
        s.copy(editor = None)
      }
    }
    def start() = {
      scope.props.flatMap{ case (props, backend) =>
        val editor = codemirror.CodeMirror.fromTextArea(codemirrorTextarea(scope).get, options(props.dark))



        editor.onChange((_, _) => 
          backend.codeChange(editor.getDoc().getValue()).runNow
        )

        scope.modState(_.copy(editor = Some(editor)))
      }
    }
  }

  type Scope = CompScope.DuringCallbackM[(App.State, App.Backend), EditorState, Backend, Element]

  private def runDelta(editor: CodeMirror, scope: Scope, current: App.State, next: App.State): Callback = {
    def setTheme() = {
      if(current.dark != next.dark) {
        val theme = 
          if(next.dark) "dark"
          else "light"

        editor.setOption("theme", s"solarized $theme")
      }
    }

    def setCode() = {
      if(current.code != next.code) {
        editor.getDoc().setValue(next.code)
      }    
    }

    def setAnnotations() = {
      val added = next.compilationInfos -- current.compilationInfos
      // Callback(
      val removed = next.compilationInfos -- current.compilationInfos
      // Callback(
      scope.modState(s => s.copy(annotations = Map()))
    }

    for {
      _ <- Callback(setTheme())
      _ <- Callback(setCode())
      _ <- setAnnotations()
    } yield ()
  }

  val component = ReactComponentB[(App.State, App.Backend)]("CodemirrorEditor")
    .initialState(EditorState())
    .backend(new Backend(_))
    .renderPS{ case (scope, (props, backend), _) => 
      textarea(defaultValue := props.code, onChange ==> backend.codeChange, ref := codemirrorTextarea, autoComplete := "off")
    }
    .componentWillReceiveProps{v => 
      val (current, _) = v.currentProps
      val (next, _) = v.nextProps
      val state = v.currentState
      val scope = v.$

      state.editor.map(editor => 
        runDelta(editor, scope, current, next)
      ).getOrElse(Callback(()))
    }
    .componentDidMount(_.backend.start())
    .componentWillUnmount(_.backend.stop())
    .build
 
  def apply(state: App.State, backend: App.Backend) = component((state, backend))
}