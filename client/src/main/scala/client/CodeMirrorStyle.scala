package client

import scalacss.Length
import scalacss.Defaults._



object CodeMirror extends StyleSheet.Standalone {
  import dsl._

  implicit class NegativeLength(v: Length[Int]) {
    def unary_- = v.copy(n = -v.n)
    def +(r: Length[Int]) = v.copy(n = v.n + r.n)
  }

  ".CodeMirror" - (
    backgroundColor.transparent,
    fontSize(16.px)

    // emphasis on not parsed (){}
    &(""".CodeMirror-code > pre > span,
         .CodeMirror-code > div > pre > span,
         .CodeMirror-linewidget pre""") - (
      fontWeight.bold,
      &.("> *") - (
        fontWeight.normal
      )
    )
  )



  ".CodeMirror, .CodeMirror-scroll" - (
    height(100.%%)
  )

// // remove spaces between inline elements
// .CodeMirror-code > pre {
//   font-size: 0;
//   & {
//     font-size: 16px;
//   }
// }


  val marginSize = 24.px

  ".CodeMirror-lines" - (
    padding(marginSize)
  )

  ".CodeMirror-linenumber" - (
    marginLeft(-marginSize)
  )

  // ".CodeMirror-linewidget .line, .CodeMirror-widget .fold" - (
  //   marginLeft(marginSize + 4.px), //(-(4.px + marginSize)),
  //   width(calc(100.%% + 4.px + marginSize))
  // )

//   padding: @margin;
//   white-space: pre-wrap;

//   p {
//     display: inline-block;
//   }

//   p, pre, h1, h2, h3, h4, h5, h6 {
//     padding: 0;
//     margin: 0;
//   }

//   ul, ol {
//     margin: 0
//   }

//   .short {
//     padding: 1px 5px 1px 5px;
//   }
//   .block {
//     display: block;
//   }

//   pre {
//     padding: 10px 0px 10px @margin/2;
//     // margin: 10px 0 10px 0;
//     display: inline-block;
//   }
// }


//   .CodeMirror-linewidget {
//     overflow: visible !important;

//     .inline {
//       border-radius: 2px;
//       position: absolute;
//       bottom: 0;
//       overflow: hidden;
//       margin-left: 20px;
//       padding-left: 5px;
//     }

//     i {
//       margin-left: 16px;
//       margin-right: 6px;
//       font-size: 12px;
//     }
//   }

//   .timeout,
//   .runtime-error,
//   .compiler.error {
//     i { color: red;}
//   }

//   .compiler.warning {
//     i { color: yellow; }
//   }

//   .runtime-error,
//   .compiler {
//     pre {
//       display: inline-block;
//     }
//   }
// }

// .CodeMirror-hints {
//   // resize: horizontal;
//   z-index: 10;
//   max-height: 400px;
//   border: none;

//   .autocomplete {
//     display: table-row;
//     max-width: none;
//     resize: none;
//     .name{
//       display: table-cell;
//     }
//     .signature{
//       padding-left: 5px;
//       display: table-cell;
//       //max-width: 600px;
//     }
//   }
// }

// .CodeMirror-dialog.CodeMirror-dialog-top {
//   border: none;
//   margin-left: 56px; // gutter size
//   line-height: 21px;

//   padding-left: 2px;
//   padding-right: 0;
//   span {
//     display: none;
//   }
//   input {
//     font-size: 18px;
//     padding: 0;
//     width: calc(~"100% - "150px) !important;
//   }
// }

// body {
//   margin: 0;
//   overflow: hidden;
// }

// ::-webkit-scrollbar:horizontal,
// .CodeMirror-simplescroll-horizontal div {
//   height: 12px;
// }

// ::-webkit-scrollbar:vertical,
// .CodeMirror-simplescroll-vertical div {
//   width: 12px;
// }

// ::-webkit-scrollbar {
//   width: 12px;
//   height: 12px;
// }

// .CodeMirror-simplescroll-horizontal,
// .CodeMirror-simplescroll-vertical {
//   div {
//     border: none;
//   }
// }

//   .autocomplete .signature {
//     color: @cyan;
//   }

//   &.cm-s-dark {
//     .CodeMirror-widget .fold,
//     .CodeMirror-linewidget .line,
//     .CodeMirror-linewidget .inline,
//     .CodeMirror-hints {
//       background-color: @base02;
//     }

//     .CodeMirror-widget .code,
//     .CodeMirror-hint-active {
//       color: inherit;
//       background-color: @base02;
//     }

//     .CodeMirror-dialog {
//       background-color: @base02;
//       color: @base01;
//     }

//     & + .menu,
//     & .menu,
//     & + .menu + #shared,
//     ::-webkit-scrollbar-thumb,
//     .CodeMirror-simplescroll-horizontal div,
//     .CodeMirror-simplescroll-vertical div,
//     .CodeMirror-hints {
//       box-shadow: 1px 1px 5px 1px rgba(0, 0, 0, 0.75);
//     }

//     ::-webkit-scrollbar-track,
//     ::-webkit-scrollbar-corner {
//       background: @base03;
//     }

//     ::-webkit-scrollbar-thumb {
//       background: @base02;
//     }

//     & + .menu,
//     & .menu,
//     & + .menu + #shared {
//       background-color: @base02;
//       color: @base01;
//       .oi:hover {
//         color: @base1;
//       }
//     }

//     .CodeMirror-simplescroll-horizontal,
//     .CodeMirror-simplescroll-vertical,
//     .CodeMirror-hints {
//       & {
//         background: @base03;
//       }

//       div {
//         background: @base02;
//       }
//     }
//   }

//   &.cm-s-light {
//     .CodeMirror-widget .fold,
//     .CodeMirror-linewidget .line,
//     .CodeMirror-linewidget .inline,
//     .CodeMirror-hints {
//       background-color: @base2;
//     }

//     .CodeMirror-widget .code,
//     .CodeMirror-hint-active {
//       color: inherit;
//       background-color: @base2;
//     }

//     .CodeMirror-dialog {
//       background-color: @base2;
//       color: @base1;
//     }

//     & + .menu,
//     & .menu,
//     & + .menu + #shared,
//     ::-webkit-scrollbar-thumb,
//     .CodeMirror-simplescroll-horizontal div,
//     .CodeMirror-simplescroll-vertical div,
//     .CodeMirror-hints {
//       box-shadow: 1px 1px 5px 1px rgba(200, 200, 200, 0.75);
//     }

//     & + .menu
//     & .menu,
//     & + .menu,
//     & + .menu + #shared {
//       background-color: @base2;
//       color: @base1;
//       .oi:hover {
//         color: @base01;
//       }
//     }

//     ::-webkit-scrollbar-track,
//     ::-webkit-scrollbar-corner {
//       background: @base3;
//     }

//     ::-webkit-scrollbar-thumb {
//       background: @base2;
//     }

//     .CodeMirror-simplescroll-horizontal,
//     .CodeMirror-simplescroll-vertical,
//     .CodeMirror-hints {
//       & {
//         background: @base3;
//       }

//       div {
//         background: @base2;
//         box-shadow: 0 0 1px grey inset;
//       }
//     }
//   }
// }

// iframe {
//   border: none;
// }

// noscript {
//   font-size: 40px;
//   position: absolute;
//   top: 50%;
//   left: 50%;
//   transform: translateX(-50%) translateY(-50%);
// }

}