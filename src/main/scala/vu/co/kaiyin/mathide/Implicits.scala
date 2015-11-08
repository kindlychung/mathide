package vu.co.kaiyin.mathide

/**
  * Created by kaiyin on 07/11/2015.
  */
object Implicits {
  implicit class Interpose[T](seq: Seq[T]) {
    def interpose(inter: T): Seq[T] = {
      @annotation.tailrec
      def _interpose(acc: Seq[T], original: Seq[T]): Seq[T] = {
        original match {
          case x if x.length == 0 => acc
          case x => _interpose(inter +: x.head +: acc, x.tail)
        }
      }
      _interpose(Seq.empty[T], seq).tail.reverse
    }
  }
}
