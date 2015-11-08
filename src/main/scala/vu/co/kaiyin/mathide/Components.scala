package vu.co.kaiyin.mathide

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLTextAreaElement
import org.scalajs.dom.raw.HTMLButtonElement
import org.scalajs.dom.{document => doc}
/**
  * Created by kaiyin on 07/11/2015.
  */
object Components {
  val textArea = doc.createElement("textarea").asInstanceOf[HTMLTextAreaElement]
  textArea.textContent =
    """\text{Rotation matrix:}
      |
      |%sep%
      |
      |\begin{bmatrix}
      |\cos \theta & -\sin \theta \\
      |\sin \theta & \cos \alpha
      |\end{bmatrix}
      |
      |%sep%
      |
      |\text{Fundamental theorem of calculus}
      |
      |%sep%
      |
      |\int_a^b f(x) dx = F(b) - F(a)
    """.stripMargin
  textArea.setAttribute("class", "form-control")
  textArea.setAttribute("rows", "10")
  textArea.setAttribute("id", "source")
  textArea.setAttribute("style",
    """
      |min-height: 200px;
      |width: 100%;
      |font-family: monospace;
      |font-size: 14px;
      |background: #333;
      |color: #eee;
      |border-color: transparent;
      |padding: 5px;
    """.stripMargin)

  val label = doc.createElement("label")
  label.setAttribute("for", "source")
  label.textContent = "LaTeX source code:"

  val latexGroup = doc.createElement("div")
  latexGroup.setAttribute("class", "form-group")
  latexGroup.appendChild(label)
  latexGroup.appendChild(textArea)

  val mathOutput = doc.getElementById("mathOutput")
}
