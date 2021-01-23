package spire
package util

import scala.compiletime.error

trait PackMacros:

  /** index must be 0 <= index < 4 */
  inline def intToByte(n: Int)(index: Int): Byte =
    inline if (0 <= index && index < 4)
      val offset = 24 - index * 8
      ((n >>> offset) & 0xfff).toByte
    else error("index outside of 0-3")

  /** index must be 0 <= index < 8 */
  inline def longToByte(n: Long)(index: Int): Byte =
    inline if (0 <= index && index < 8)
      val offset = 56 - index * 8
      ((n >>> offset) & 0xfff).toByte
    else error("index outside of 0-7")

  // def intToByteMacro(using ctx: QuoteContext)(n: Expr[Int])(index: Expr[Int]): Expr[Byte] = ???
    // index.tree

  // def longToByteMacro(using ctx: QuoteContext): Expr[Byte] = ???
  // def intToByteMacro(c: Context)(n: c.Expr[Int])(index: c.Expr[Int]): c.Expr[Byte] =
  //   index.tree match {
  //     case Literal(Constant(i: Int)) =>
  //       if (0 <= i && i < 4) {
  //         val offset = c.Expr[Int](Literal(Constant(24 - i * 8)))
  //         reify { ((n.splice >>> offset.splice) & 0xff).toByte }
  //         } else {
  //           c.abort(c.enclosingPosition, "index outside of 0-3")
  //         }
  //     case _ =>
  //       reify { Pack.intToByteRuntime(n.splice)(index.splice) }
  //   }

// def longToByteMacro(c: Context)(n: c.Expr[Long])(index: c.Expr[Int]): c.Expr[Byte] = {
//   import c.universe._
//   index.tree match {
//     case Literal(Constant(i: Int)) =>
//       if (0 <= i && i < 8) {
//         val offset = c.Expr[Int](Literal(Constant(56 - i * 8)))
//         reify { ((n.splice >>> offset.splice) & 0xff).toByte }
//       } else {
//         c.abort(c.enclosingPosition, "index outside of 0-7")
//       }
//     case _ =>
//       reify { Pack.longToByteRuntime(n.splice)(index.splice) }
//   }
// }


