package client

import scalacss.Defaults._
import scalacss.mutable.Register

class Solarized(implicit r: Register) extends StyleSheet.Inline()(r) {
  import dsl._

  // dark
  val base00  = c"#657b83"
  val base01  = c"#586e75"
  val base02  = c"#073642"
  val base03  = c"#002b36"

  // light
  val base0   = c"#839496"
  val base1   = c"#93a1a1"
  val base2   = c"#eee8d5"
  val base3   = c"#fdf6e3"

  // common
  val yellow  = c"#b58900"
  val orange  = c"#cb4b16"
  val red     = c"#dc322f"
  val magenta = c"#d33682"
  val violet  = c"#6c71c4"
  val blue    = c"#268bd2"
  val cyan    = c"#2aa198"
  val green   = c"#859900"
}

object CodeMirrorSolarizedTheme extends StyleSheet.Standalone {
  import dsl._

  val s = new Solarized
  import s._


  ".cm-s-solarized" - (

    // color-profile: sRGB;
    // rendering-intent: auto;

    &(".cm-keyword") - color(s.yellow),
    &(".cm-atom") - color(s.yellow),
    &(".cm-number") - color(s.magenta),
    &(".cm-def") - color(s.blue),
    &(".cm-variable") - color(s.cyan),
    &(".cm-variable-2") - color(s.cyan),
    &(".cm-variable-3") - color(s.cyan),
    &(".cm-property") - color(s.cyan),
    &(".cm-operator") - color(s.yellow),
    &(".cm-comment") - color(base01),
    &(".cm-string") - color(s.green),
    &(".cm-string-2") - color(s.yellow),
    &(".cm-meta") - color(s.green),
    &(".cm-error, .cm-invalidchar") - (
      color(base01)//,
      // borderBottom := s"1px dotted ${s.red}"
    ),
    &(".cm-qualifier") - color(s.yellow),
    &(".cm-builtin") - color(s.magenta),
    &(".cm-bracket") - color(s.orange)
  )

}

//     .CodeMirror-matchingbracket" - ( 
//       color(green !important)
//       ".cm-matchhighlight;
//     ),
//     .CodeMirror-nonmatchingbracket" - (
//       color(red !important;
//       ".cm-matchhighlight;
//     ),
//     ".cm-tag" - ( color(base1)),
//     ".cm-attribute" - (  color(cyan)),
//     ".cm-header" - ( color(base01)),
//     ".cm-quote" - ( color(base1)),
//     ".cm-hr" - (
//       color: transparent;
//       border-top: 1px solid @base01;
//       display: block;
//     ),
//     ".cm-link" - ( color(base1)cursor: pointer)),
//     ".cm-special" - ( color(violet)),
//     ".cm-em" - (
//       color: #999;
//       text-decoration: underline;
//       text-decoration-style: dotted;
//     ),
//     ".cm-strong" - ( color: #eee)),
//     ".cm-tab:before" - ( color(base01)),

//     ".cm-matchhighlight" - (
//       border-bottom-style: solid;
//       border-bottom-width: 2px;
//     ),

//     .CodeMirror-gutters" - (
//       background-color(base02;
//       border-right-color: transparent;
//     ),

//     a" - (
//       color(blue;
//     ),

//     &".cm-s-dark" - (
//       color(base0;
//       background-color:  @base03;
//       ".cm-matchhighlight" - (
//         border-bottom-color(base02;
//       ),
//       .CodeMirror-gutters" - (
//         background-color(base02;
//       ),
//       .CodeMirror-gutter-filler" - (
//         background-color(base03;
//       ),
//       .CodeMirror-selected, ".cm-searching" - (
//         background: rgba(255, 255, 255, 0.10);
//       ),
//       .CodeMirror-activeline-background" - (
//         background-color(base02;
//       ),
//       .CodeMirror-scrollbar-filler" - (
//         background-color:  @base03;
//       ),
//     ),

//     &".cm-s-light" - (
//       background-color(base3;
//       color(base00;

//       ".cm-matchhighlight" - (
//         border-bottom-color(base2;
//       ),

//       .CodeMirror-gutters" - (
//         background-color(base2;
//       ),

//       .CodeMirror-gutter-filler" - (
//         background-color(base3;
//       ),

//       .CodeMirror-selected, ".cm-searching" - (
//         background: rgba(0, 0, 0, 0.10);
//       ),

//       .CodeMirror-activeline-background" - (
//         background-color(base2;
//       ),

//       .CodeMirror-scrollbar-filler" - (
//         background-color:  @base3;
//       ),
//     ),

//     .CodeMirror-gutters" - (
//       padding: 0 15px 0 10px;
//     ),

//     .CodeMirror-linenumber" - (
//       color(base00;
//     ),

//     .CodeMirror-gutter .CodeMirror-gutter-text" - (
//       color(base01;
//     ),

//     .CodeMirror-lines .CodeMirror-cursor" - (
//       border-left: 1px solid @base01;
//     ),
// }

// }
