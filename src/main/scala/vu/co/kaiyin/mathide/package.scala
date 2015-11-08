package vu.co.kaiyin

import org.scalajs.dom.raw.HTMLTextAreaElement
import collection.mutable.{Map => MMap}

class WordEntry(var word: String, val meta: String, var freq: Int)

object WordEntry {
  def apply(_word: String, _meta: String, _freq: Int) = {
    new WordEntry(_word, _meta, _freq)
  }
}

/**
  * Created by kaiyin on 07/11/2015.
  */
package object mathide {
  private def makeEntries(wordList: String, meta: String, freq: Int = 0) = wordList.split( """\s+""").map {
    s => WordEntry(
      if (meta == "environ")
        s"\\begin{$s}\n\n\\end{$s}"
      else s,
      meta,
      freq
    )
  }

  private val environs: Array[WordEntry] = makeEntries("array matrix bmatrix Bmatrix " +
    "pmatrix vmatrix Vmatrix cases", "environ")
  private val extraneous = makeEntries( """\sqrt \text \color \overline \rule \KaTeX""", "extraneous")
  private val delimiterSizes = makeEntries(
    """\bigl \Bigl \biggl \Biggl
      |\bigr \Bigr \biggr \Biggr
      |\bigm \Bigm \biggm \Biggm
      |\big \Big \bigg \Bigg \left \right""".stripMargin, "delimiter control")
  private val operators = makeEntries(
    """\approx \cong \ge \geq \le \leq
      |\ne \neq \ngeq \nleq \in \cdot \circ \div \pm \times
      |\arcsin \arccos \arctan \arg \cos \cosh \cot \coth
      |\csc \deg \dim \exp \hom \ker \lg \ln \log \sec \sin
      |\sinh \tan \tanh \det \gcd \inf \lim \liminf \limsup
      |\max \min \Pr \sup \int \iint \iiint \oint \coprod
      |\bigvee \bigwedge \biguplus \bigcap \bigcup \curlyvee \curlywedge
      |\doublecap \doublecup \intop \prod
      |\sum \bigotimes \bigoplus \bigodot \bigsqcup \smallint""".stripMargin, "operator")
  private val fractions = makeEntries( """\over \frac \dfrac \tfrac""", "fraction")
  private val binomials = makeEntries( """\choose \binom \dbinom \tbinom""", "binomial")
  private val overlaps = makeEntries( """\rlap \llap""", "overlap")
  private val sizes = makeEntries(
    """\tiny \scriptsize \footnotesize \small
      |\normalsize \large \Large \LARGE \huge \Huge""".stripMargin, "sizing")
  private val styles = makeEntries(
    """\displaystyle \textstyle \scriptstyle
      |\scriptscriptstyle \limits \nolimits""".stripMargin, "style")
  private val accents = makeEntries(
    """\acute \grave \ddot \tilde
      |\bar \breve \check \hat \vec \dot""".stripMargin, "accent")
  private val greeks = makeEntries(
    """\Gamma \Delta \Theta \Lambda \Xi \Pi \Sigma \""" + """Upsilon
      |\Phi \Psi \Omega \alpha \beta \gamma \delta \epsilon \zeta \eta \theta \iota \kappa
      |\lambda \mu \nu \xi \omicron \pi \rho \sigma \tau \"""+ """upsilon \phi
      |\chi \psi \omega \varepsilon \vartheta \varpi \varrho \varsigma \varphi""".stripMargin, "greek")
  private val arrows = makeEntries(
    """\gets \leftarrow \LeftArrow \leftrightarrow
      |\Leftrightarrow \rightarrow \Rightarrow
      |\nleftarrow \nLeftarrow \nleftrightarrow \nLeftrightarrow
      |\nrightarrow \nRightarrow \to""".stripMargin, "arrow")
  private val fonts = makeEntries(
    """\mathrm \mathit \mathbf \mathbb
      |\mathcal \mathfrak \mathscr \mathsf \mathtt \Bbb \bold \frak""".stripMargin, "font")
  private val spaces = makeEntries( """\enspace \qquad \quad \space \phantom""", "spacing")
  private val symbols = makeEntries(
    """\surd \barwedge \veebar \box \boxdot \bigtriangleup
      |\bigtriangledown \checkmark \dagger \diamond \Diamond \star \triangleleft
      |\triangleright \angle \infty \prime \triangle \yen \imath \jmath \circledast
      |\circledcirc \circleddash \circledS \circledR \odot \oplus \otimes \oslash""".stripMargin, "symbol")
  private val delimiters = makeEntries(
    """\lbrace \rbrace \lbrack \rbrack \lfloor \rfloor %sep%
      |\lceil \rceil \backslash \vert \Vert \""".stripMargin +
      """uparrow \""" + """Uparrow \downarrow \Downarrow \"""+
    """updownarrow \""" +
    """Updownarrow \langle \lvert \rangle \rvert \lgroup
      |\rgroup \lmoustache \rmoustache""".stripMargin, "delimiter")

  val wordLib = environs ++ extraneous ++ delimiterSizes ++ operators ++ fractions ++
    binomials ++ overlaps ++ sizes ++ styles ++ accents ++
    greeks ++ arrows ++ fonts ++ spaces ++ symbols ++ delimiters
}
