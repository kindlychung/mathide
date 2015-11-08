package vu.co.kaiyin.mathide

import rx.core.{Obs, Rx, Var}
import vu.co.kaiyin.WordEntry
import vu.co.kaiyin.mathide.Implicits._
import org.scalajs.dom.raw.{HTMLInputElement, KeyboardEvent, HTMLTextAreaElement}
import org.scalajs.dom.{Event, document => doc, html}
import vu.co.kaiyin.katexjs.{RenderOpts, katex}
import vu.co.kaiyin.mathide.Components._

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSExport
import scala.util.matching.Regex

/**
  * Created by kaiyin on 06/11/2015.
  */
@JSExport
class Main {

  @JSExport
  def main(mainDiv: html.Div): Unit = {

    val renderMath: js.Function1[Event, _] = (e: Event) => {
      mathOutput.innerHTML = ""
      textArea.value.split( """(%sep%(\s*))+""").map(texString => {
        val p = doc.createElement("p")
        mathOutput.appendChild(p)
        katex.render(texString, p, RenderOpts(true, true))
      })
    }

    val clearTextarea: js.Function1[Event, _] = (e: Event) => {
      textArea.value = ""
    }


    def wordToPos(string: String, position: Int): String = {
      val lastWordPattern = """([\s\S]*)\s+([\s\S]*)""".r
      val subString = string.substring(0, position)
      subString match {
        case lastWordPattern(_, word) => {
          word
        }
        case _ => ""
      }
    }

    object textWorld {
      var caretPos: Int = 0
      var content: String = ""
      var lastWord: String = ""
      var posBeforeLastWord: Int = 0
      var string1: String = _
      var string2: String = _
      var lastWordPattern1: Regex = _
      var lastWordPattern2: Regex = _
      var matchedEntries: List[WordEntry] = Nil
      var matchedIndex: Option[Int] = None
      var matchedEntry: String = ""

      def updateCaretPos: Unit = {
        caretPos = textArea.selectionStart
      }

      def updateContent: Unit = {
        content = textArea.value
      }

      def updateLastWord: Unit = {
        lastWord = wordToPos(content, caretPos)
      }

      def updatePosBeforeLastWord: Unit = {
        posBeforeLastWord = caretPos - lastWord.length
      }

      def updateString1: Unit = {
        string1 = content.substring(0, posBeforeLastWord)
      }

      def updateString2 = {
        string2 = content.substring(caretPos)
      }

      def updateLastWordPattern1: Unit = {
        lastWordPattern1 = if (lastWord.startsWith( """\""")) lastWord.tail.r else lastWord.r
      }

      def updateLastWordPattern2: Unit = {
        lastWordPattern2 = lastWordPattern1.unanchored
      }

      def updateMatchedEntries: Unit = {
        matchedEntries = wordLib.filter(x => lastWordPattern1.findFirstIn(x.word).nonEmpty).toList
        if(matchedEntries.isEmpty) {
          matchedEntries = wordLib.filter(x => lastWordPattern2.findFirstIn(x.word).nonEmpty).toList
        }
      }

      def updateMatchedIndex(i: Int = 0): Unit = {
        if(matchedEntries.isEmpty) {
          matchedIndex = None
        } else {
          matchedIndex = Some(i)
        }
      }

      def updateMatchedEntry: Unit = {
        matchedEntry = matchedIndex.map(x => matchedEntries(x).word).getOrElse(lastWord)
      }

      def newValue = string1 + matchedEntry + string2

      def updateWorld: Unit = {
        updateCaretPos
        updateContent
        updateLastWord
        updatePosBeforeLastWord
        updateString1
        updateString2
        updateLastWordPattern1
        updateLastWordPattern2
        updateMatchedEntries
        updateMatchedIndex()
        updateMatchedEntry
      }
    }



    renderMath(null)
    textArea.onkeydown = (e: KeyboardEvent) => {
      if (e.keyCode == 9 || e.ctrlKey && e.keyCode == 32) {
        e.preventDefault()
        textWorld.updateWorld
        println(textWorld.lastWord)
        println(textWorld.matchedEntry)
        println(textWorld.matchedIndex)
        textWorld.matchedEntries.foreach(x => println(x.word))
        if(textWorld.matchedEntry != textWorld.lastWord) {
          textArea.value = textWorld.newValue
        }
      } else if (e.ctrlKey && e.keyCode == 78) {
        textWorld.matchedIndex match {
          case None =>
          case Some(i) => {
            textWorld.updateMatchedIndex((i + 1) % textWorld.matchedEntries.length)
            textWorld.updateMatchedEntry
            textArea.value = textWorld.newValue
          }
        }
      } else if (e.ctrlKey && e.keyCode == 80) {
        textWorld.matchedIndex match {
          case None =>
          case Some(i) => {
            val l = textWorld.matchedEntries.length
            textWorld.updateMatchedIndex((i - 1 + l) % l)
            textWorld.updateMatchedEntry
            textArea.value = textWorld.newValue
          }
        }
      } else if(e.keyCode == 32 || e.keyCode == 10 || e.keyCode == 13) {
        textWorld.updateWorld
      }
      renderMath(e)
    }


    mainDiv.appendChild(latexGroup)
  }
}
