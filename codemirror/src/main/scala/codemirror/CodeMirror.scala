package codemirror

import org.scalajs.dom.raw.{HTMLTextAreaElement, Element}
import scala.scalajs.js
import js.annotation._
import js.{|, UndefOr} // Dictionary, RegExp

@js.native
@JSName("CodeMirror")
object CodeMirror extends js.Object {
  def apply(element: Element, options: UndefOr[Options]): Editor = js.native
  def fromTextArea(textarea: HTMLTextAreaElement, options: Options): TextAreaEditor = js.native
}

@ScalaJSDefined
trait Editor extends js.Object {
  // def replaceSelection(spaces)
  def hasFocus(): Boolean
  // def findPosH(start: Pos, amount: Int, unit: String, visually: Boolean):{line, ch, ?hitSide: Boolean}
  // def findPosV(start: Pos, amount: Int, unit: String):{line, ch, ?hitSide: Boolean}
  // def findWordAt(pos: Pos):{anchor: Pos, head: Pos}
  def setOption(option: String, value: js.Any): Unit
  def getOption(option: String): js.Any
  def addKeyMap(map: js.Object, bottom: Boolean): Unit
  def removeKeyMap(map: js.Object): Unit
  def addOverlay(mode: String | js.Object, options: UndefOr[js.Object]): Unit
  def removeOverlay(mode: String|js.Object): Unit
  // def on(type: String, func: (...args)): Unit
  // def off(type: String, func: (...args)): Unit
  def getDoc(): Document
  def swapDoc(document: Document): Document
  def setGutterMarker(line: Int | LineHandle, gutterID: String, value: Element): LineHandle
  def clearGutter(gutterID: String): Unit
  def lineInfo(line: Int|LineHandle): js.Object
  def addWidget(pos: Pos, node: Element, scrollIntoView: Boolean): Unit
  def setSize(width: Int | String, height: Int | String): Unit
  def scrollTo(x: Int, y: Int): Unit
  // def getScrollInfo():{left, top, width, height, clientWidth, clientHeight}
  // def scrollIntoView(what: Pos|{left, top, right, bottom}|{from, to}|null, ?margin: number)
  // def cursorCoords(where: Boolean | Pos , mode: String):{left, top, bottom}
  // def charCoords(pos: Pos, ?mode: String): {left, right, top, bottom}
  // def coordsChar(js.Object: {left, top}, ?mode: String): Pos
  def lineAtHeight(height: Int, mode: UndefOr[String]): Int
  def heightAtLine(line: Int | LineHandle, mode: UndefOr[String]): Int
  def defaultTextHeight(): Int
  def defaultCharWidth(): Int
  // def getViewport():{from: number, to: number}
  def refresh(): Unit
  def getModeAt(pos: Pos): js.Object
  def getTokenAt(pos: Pos, precise: UndefOr[Boolean]): js.Object
  // def getLineTokens(line: Int, ?precise: Boolean): array<{start, end, String, type, state}>
  def getTokenTypeAt(pos: Pos): String
  // def getHelpers(pos: Pos, type: String): array<helper>
  // def getHelper(pos: Pos, type: String): helper
  // def getStateAfter(?line: Int, ?precise: Boolean): js.Object
  // def operation(func: ():any): any
  def indentLine(line: Int, dir: UndefOr[String | Int]): Unit
  def toggleOverwrite(value: UndefOr[Boolean]): Unit
  def isReadOnly(): Boolean
  def execCommand(name: String): Unit
  def focus(): Unit
  def getInputField(): Element
  def getWrapperElement(): Element
  def getScrollerElement(): Element
  def getGutterElement(): Element
}

@ScalaJSDefined
trait TextAreaEditor extends Editor {
  def save(): Unit
  def toTextArea(): Unit
  def getTextArea: HTMLTextAreaElement
}

@ScalaJSDefined
class Pos extends js.Object {
  var line: Int = 0
  var ch: Int = 0
}

@ScalaJSDefined
trait LineHandle extends js.Object

@ScalaJSDefined
trait LineWidget extends js.Object

@ScalaJSDefined
trait TextMarker extends js.Object

@ScalaJSDefined
trait Document extends js.Object {
  def getValue(separator: UndefOr[String]): String
  def setValue(content: String)
  def getRange(from: Pos, to: Pos, separator: UndefOr[String]): String
  def replaceRange(replacement: String, from: Pos, to: Pos, origin: UndefOr[String])
  def getLine(n: Int): String
  def lineCount(): Int
  def firstLine(): Int
  def lastLine(): Int
  def getLineHandle(num: Int): LineHandle
  def getLineNumber(handle: LineHandle): Int
  // def eachLine(f: (line: LineHandle))
  // def eachLine(start: Int, end: Int, f: (line: LineHandle))
  def markClean()
  def changeGeneration(closeEvent: UndefOr[Boolean]): Int
  def isClean(generation: UndefOr[Int]): Boolean
  def getSelection(lineSep: UndefOr[String]): String
  def getSelections(lineSep: UndefOr[String]): Array[String]
  def replaceSelection(replacement: String, select: UndefOr[String])
  def replaceSelections(replacements: Array[String], select: UndefOr[String])
  def getCursor(start: UndefOr[String]): Pos
  // def listSelections(): array<{anchor, head}>
  def somethingSelected(): Boolean
  def setCursor(pos: Pos | Int, ch: UndefOr[Int], options: UndefOr[js.Object])
  def setSelection(anchor: Pos, head: UndefOr[Pos], options: UndefOr[js.Object])
  // def setSelections(ranges: array<{anchor, head}>, primary: UndefOr[Int], options: UndefOr[js.Object])
  def addSelection(anchor: Pos, head: UndefOr[Pos])
  def extendSelection(from: Pos, to: UndefOr[Pos], options: UndefOr[js.Object])
  def extendSelections(heads: Array[Pos], options: UndefOr[js.Object])
  // def extendSelectionsBy(f: function(range: {anchor, head}):Pos), ?
  def setExtending(value: Boolean)
  def getExtending(): Boolean
  def getEditor(): Editor
  def copy(copyHistory: Boolean): Document
  def linkedDoc(options: js.Object): Document
  def unlinkDoc(doc: Document)
  def iterLinkedDocs(function: (Document, Boolean) => Unit)
  def undo(): Unit
  def redo(): Unit
  def undoSelection(): Unit
  def redoSelection(): Unit
  // def historySize(): {undo: Int, redo: Int}
  def clearHistory()
  def getHistory(): js.Object
  def setHistory(history: js.Object)
  def markText(from: Pos, to: Pos, options: UndefOr[js.Object]): TextMarker
  def setBookmark(pos: Pos, options: UndefOr[js.Object]): TextMarker
  def findMarks(from: Pos, to: Pos): Array[TextMarker]
  def findMarksAt(pos: Pos): Array[TextMarker]
  def getAllMarks(): Array[TextMarker]
  def addLineClass(line: Int | LineHandle, where: String, `class`: String): LineHandle
  def removeLineClass(line: Int | LineHandle, where: String, `class`: String): LineHandle
  def addLineWidget(line: Int | LineHandle, node: Element, options: UndefOr[js.Object]): LineWidget
  def getMode(): js.Object
  def lineSeparator()
  def posFromIndex(index: Int): Pos
  def indexFromPos(pos: Pos): Int
}

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

