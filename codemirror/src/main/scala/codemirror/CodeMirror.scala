package codemirror

import org.scalajs.dom.raw.{HTMLTextAreaElement, Element}
import scala.scalajs.js
import js.annotation._
import js.UndefOr // Dictionary, RegExp

@js.native
@JSName("CodeMirror")
object CodeMirror extends js.Object {
  def apply(element: Element, options: UndefOr[Options]): Editor = js.native
  def fromTextArea(textarea: HTMLTextAreaElement, options: Options): TextAreaEditor = js.native
}

@ScalaJSDefined
@JSName("CodeMirror.Pos")
class Position extends js.Object {
  var line: Int = 0
  var ch: Int = 0
}

@ScalaJSDefined
trait LineHandle extends js.Object

@ScalaJSDefined
trait LineWidget extends js.Object {
  def clear(): Unit
}

@ScalaJSDefined
trait TextMarker extends js.Object

@ScalaJSDefined
trait Options extends js.Object
// {
//   val value                       : UndefOr[String | Document]
//   val mode                        : UndefOr[String | js.Object]
//   val lineSeparator               : UndefOr[String]
//   val theme                       : UndefOr[String]
//   val indentUnit                  : UndefOr[Int]
//   val smartIndent                 : UndefOr[Boolean]
//   val tabSize                     : UndefOr[Int]
//   val indentWithTabs              : UndefOr[Boolean]
//   val electricChars               : UndefOr[Boolean]
//   val specialChars                : UndefOr[RegExp]
//   val specialCharPlaceholder      : UndefOr[Char => Element]
//   val rtlMoveVisually             : UndefOr[Boolean]
//   val keyMap                      : UndefOr[String]
//   val extraKeys                   : UndefOr[Dictionary[String]]
//   val lineWrapping                : UndefOr[Boolean]
//   val lineNumbers                 : UndefOr[Boolean]
//   val firstLineNumber             : UndefOr[Int]
//   val lineNumberFormatter         : UndefOr[Int => String]
//   val gutters                     : UndefOr[Array[String]]
//   val fixedGutter                 : UndefOr[Boolean]
//   val scrollbarStyle              : UndefOr[String]
//   val coverGutterNextToScrollbar  : UndefOr[Boolean]
//   val inputStyle                  : UndefOr[String]
//   val readOnly                    : UndefOr[Boolean|String]
//   val showCursorWhenSelecting     : UndefOr[Boolean]
//   val lineWiseCopyCut             : UndefOr[Boolean]
//   val undoDepth                   : UndefOr[Int]
//   val historyEventDelay           : UndefOr[Int]
//   val tabindex                    : UndefOr[Int]
//   val autofocus                   : UndefOr[Boolean]
//   val dragDrop                    : UndefOr[Boolean]
//   val allowDropFileTypes          : UndefOr[Array[String]]
//   val cursorBlinkRate             : UndefOr[Double]
//   val cursorScrollMargin          : UndefOr[Double]
//   val cursorHeight                : UndefOr[Double]
//   val resetSelectionOnContextMenu : UndefOr[Boolean]
//   val workTime                    : UndefOr[Double]
//   val workDelay                   : UndefOr[Double]
//   val pollInterval                : UndefOr[Double]
//   val flattenSpans                : UndefOr[Boolean]
//   val addModeClass                : UndefOr[Boolean]
//   val maxHighlightLength          : UndefOr[Double]
//   val viewportMargin              : UndefOr[Int]
// }

