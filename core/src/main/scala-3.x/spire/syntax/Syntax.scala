package spire
package syntax

import spire.NoImplicit
import spire.algebra._
import spire.algebra.lattice._
import spire.algebra.partial._
import spire.math._
// import spire.macros.Syntax
import spire.syntax.std._
import scala.annotation.nowarn
import scala.annotation.targetName

trait EqSyntax {
  implicit def eqOps[A: Eq](a: A): EqOps[A] = new EqOps(a)
}

trait PartialOrderSyntax extends EqSyntax {
  extension [A](lhs: A)(using po: PartialOrder[A])
    infix def >(rhs: A): Boolean = lhs > rhs
    infix def >=(rhs: A): Boolean = lhs >= rhs
    infix def <(rhs: A): Boolean = lhs < rhs
    infix def <=(rhs: A): Boolean = lhs <= rhs

    def partialCompare(rhs: A): Double = po.partialCompare(lhs, rhs)
    def tryCompare(rhs: A): Option[Int] = po.tryCompare(lhs, rhs)
    def pmin(rhs: A): Option[A] = po.pmin(lhs, rhs)
    def pmax(rhs: A): Option[A] = po.pmin(lhs, rhs)

    infix def >(rhs: Int)(implicit ev1: Ring[A]): Boolean = lhs > ev1.fromInt(rhs)
    infix def >=(rhs: Int)(implicit ev1: Ring[A]): Boolean = lhs >= ev1.fromInt(rhs)
    infix def <(rhs: Int)(implicit ev1: Ring[A]): Boolean = lhs < ev1.fromInt(rhs)
    infix def <=(rhs: Int)(implicit ev1: Ring[A]): Boolean = lhs <= ev1.fromInt(rhs)

    infix def >(rhs: Double)(implicit ev1: Field[A]): Boolean = lhs > ev1.fromDouble(rhs)
    infix def >=(rhs: Double)(implicit ev1: Field[A]): Boolean = lhs >= ev1.fromDouble(rhs)
    infix def <(rhs: Double)(implicit ev1: Field[A]): Boolean = lhs < ev1.fromDouble(rhs)
    infix def <=(rhs: Double)(implicit ev1: Field[A]): Boolean = lhs <= ev1.fromDouble(rhs)

    infix def >(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) > rhs
    infix def >=(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) >= rhs
    infix def <(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) < rhs
    infix def <=(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) <= rhs
}

trait OrderSyntax extends PartialOrderSyntax {
  // implicit def orderOps[A: Order](a: A): OrderOps[A] = new OrderOps(a)
  extension [A](lhs: A)(using o: Order[A])
    def compare(rhs: A): Int = o.compare(lhs, rhs)
    def min(rhs: A): A = o.min(lhs, rhs)
    def max(rhs: A): A = o.max(lhs, rhs)

    // def compare(rhs: Int)(implicit ev1: Ring[A]): Int = compare(ev1.fromInt(rhs))
    def min(rhs: Int)(implicit ev1: Ring[A]): A = min(ev1.fromInt(rhs))
    // def max(rhs: Int)(implicit ev1: Ring[A]): A = max(ev1.fromInt(rhs))

    // def compare(rhs: Double)(implicit ev1: Field[A]): Int = compare(ev1.fromDouble(rhs))
    def min(rhs: Double)(implicit ev1: Field[A]): A = min(ev1.fromDouble(rhs))
    // def max(rhs: Double)(implicit ev1: Field[A]): A = max(ev1.fromDouble(rhs))

    // def compare(rhs: Number)(implicit c: ConvertableFrom[A]): Int = c.toNumber(lhs).compare(rhs)
    def min(rhs: Number)(implicit c: ConvertableFrom[A]): Number = c.toNumber(lhs).min(rhs)
    // def max(rhs: Number)(implicit c: ConvertableFrom[A]): Number = c.toNumber(lhs).max(rhs)

  // extension (lhs: Int)
  //   def <[A](rhs: A)(implicit ev: Order[A], c: ConvertableTo[A]): Boolean = ev.lt(c.fromInt(lhs), rhs)
  //   def <=[A](rhs: A)(implicit ev: Order[A], c: ConvertableTo[A]): Boolean = ev.lteqv(c.fromInt(lhs), rhs)
  //   def >[A](rhs: A)(implicit ev: Order[A], c: ConvertableTo[A]): Boolean = ev.gt(c.fromInt(lhs), rhs)
  //   def >=[A](rhs: A)(implicit ev: Order[A], c: ConvertableTo[A]): Boolean = ev.gteqv(c.fromInt(lhs), rhs)
  //
  //   def cmp[A](rhs: A)(implicit ev: Order[A], c: ConvertableTo[A]): Int = ev.compare(c.fromInt(lhs), rhs)
  //   def min[A](rhs: A)(implicit ev: Order[A], c: ConvertableTo[A]): A = ev.min(c.fromInt(lhs), rhs)
  //   def max[A](rhs: A)(implicit ev: Order[A], c: ConvertableTo[A]): A = ev.max(c.fromInt(lhs), rhs)
    //
  // implicit def literalIntOrderOps(lhs: Int): LiteralIntOrderOps = new LiteralIntOrderOps(lhs)
  // implicit def literalLongOrderOps(lhs: Long): LiteralLongOrderOps = new LiteralLongOrderOps(lhs)
  // implicit def literalDoubleOrderOps(lhs: Double): LiteralDoubleOrderOps = new LiteralDoubleOrderOps(lhs)
}

trait SignedSyntax extends OrderSyntax {
  extension [A](a: A)(using s: Signed[A])
    def abs(): A = s.abs(a)
    def sign(): Sign = s.sign(a)
    def signum(): Int = s.signum(a)

    def isSignZero(): Boolean = s.isSignZero(a)
    def isSignPositive(): Boolean = s.isSignPositive(a)
    def isSignNegative(): Boolean = s.isSignNegative(a)

    def isSignNonZero(): Boolean = s.isSignNonZero(a)
    def isSignNonPositive(): Boolean = s.isSignNonPositive(a)
    def isSignNonNegative(): Boolean = s.isSignNonNegative(a)
}

trait TruncatedDivisionSyntax extends SignedSyntax {
  implicit def truncatedDivisionOps[A: TruncatedDivision](a: A): TruncatedDivisionOps[A] = new TruncatedDivisionOps(a)
  implicit def literalIntTruncatedDivisionOps(lhs: Int): LiteralIntTruncatedDivisionOps =
    new LiteralIntTruncatedDivisionOps(lhs)
  implicit def literalLongTruncatedDivisionOps(lhs: Long): LiteralLongTruncatedDivisionOps =
    new LiteralLongTruncatedDivisionOps(lhs)
  implicit def literalDoubleTruncatedDivisionOps(lhs: Double): LiteralDoubleTruncatedDivisionOps =
    new LiteralDoubleTruncatedDivisionOps(lhs)
}

trait InvolutionSyntax {
  implicit def involutionOps[A: Involution](lhs: A): InvolutionOps[A] = new InvolutionOps(lhs)
}

trait IsRealSyntax extends SignedSyntax {
  extension [A](lhs: A)(using is: IsReal[A])
  // def isWhole(): Boolean = macro Ops.unop[Boolean]
    def ceil(): A = is.ceil(lhs)
    def floor(): A = is.floor(lhs)
    def round(): A = is.round(lhs)
  // //def toDouble(): Double = macro Ops.unop[Double]
}

trait SemigroupoidSyntax {
  implicit def semigroupoidOps[A: Semigroupoid](a: A): SemigroupoidOps[A] = new SemigroupoidOps[A](a)
}

trait GroupoidSyntax extends SemigroupoidSyntax {
  @nowarn
  implicit def groupoidCommonOps[A](a: A)(implicit ev: Groupoid[A], ni: NoImplicit[Monoid[A]]): GroupoidCommonOps[A] =
    new GroupoidCommonOps[A](a)(ev)
  implicit def groupoidOps[A](a: A)(implicit ev: Groupoid[A]): GroupoidOps[A] = new GroupoidOps[A](a)
}

trait SemigroupSyntax {
  implicit def semigroupOps[A: Semigroup](a: A): SemigroupOps[A] = new SemigroupOps(a)
}

trait MonoidSyntax extends SemigroupSyntax {
  implicit def monoidOps[A](a: A)(implicit ev: Monoid[A]): MonoidOps[A] = new MonoidOps(a)
}

trait GroupSyntax extends MonoidSyntax {
  implicit def groupOps[A: Group](a: A): GroupOps[A] = new GroupOps(a)
}

trait AdditiveSemigroupSyntax {
  extension [A](lhs: A)(using as: AdditiveSemigroup[A])
    def +(rhs: A): A = as.plus(lhs, rhs)
    @targetName("plus")
    def ^+(rhs: A): A = as.plus(lhs, rhs)
    def +(rhs: Int)(implicit ev1: Ring[A]): A = as.plus(lhs, ev1.fromInt(rhs))
    def +(rhs: Double)(implicit ev1: Field[A]): A = as.plus(lhs, ev1.fromDouble(rhs))
    def +(rhs: Number)(using c: ConvertableFrom[A]): Number = c.toNumber(lhs) + rhs

  extension(lhs: Int)
    def +[A](rhs: A)(using ev: Ring[A]): A = ev.plus(ev.fromInt(lhs), rhs)

  // implicit def literalIntAdditiveSemigroupOps(lhs: Int): LiteralIntAdditiveSemigroupOps =
  //   new LiteralIntAdditiveSemigroupOps(lhs)
  implicit def literalLongAdditiveSemigroupOps(lhs: Long): LiteralLongAdditiveSemigroupOps =
    new LiteralLongAdditiveSemigroupOps(lhs)
  implicit def literalDoubleAdditiveSemigroupOps(lhs: Double): LiteralDoubleAdditiveSemigroupOps =
    new LiteralDoubleAdditiveSemigroupOps(lhs)
}

trait AdditiveMonoidSyntax extends AdditiveSemigroupSyntax {
  // implicit def additiveMonoidOps[A](a: A)(implicit ev: AdditiveMonoid[A]): AdditiveMonoidOps[A] = new AdditiveMonoidOps(
  //   a
  // )
  extension [A](lhs: A)(using am: AdditiveMonoid[A])
    def isZero(implicit ev1: Eq[A]): Boolean = am.isZero(lhs)
}

trait AdditiveGroupSyntax extends AdditiveMonoidSyntax {
  extension [A](lhs: A)(using ag: AdditiveGroup[A])
    def unary_- : A = ag.negate(lhs)
    def -(rhs: A): A = ag.minus(lhs, rhs)
    // def -(rhs: Int)(implicit ev1: Ring[A]): A = macro Ops.binopWithLift[Int, Ring[A], A]
    // def -(rhs: Double)(implicit ev1: Field[A]): A = macro Ops.binopWithLift[Double, Field[A], A]
    def -(rhs: Number)(implicit c: ConvertableFrom[A]): Number = c.toNumber(lhs) - rhs

  implicit def literalIntAdditiveGroupOps(lhs: Int): LiteralIntAdditiveGroupOps = new LiteralIntAdditiveGroupOps(lhs)
  implicit def literalLongAdditiveGroupOps(lhs: Long): LiteralLongAdditiveGroupOps = new LiteralLongAdditiveGroupOps(
    lhs
  )
  implicit def literalDoubleAdditiveGroupOps(lhs: Double): LiteralDoubleAdditiveGroupOps =
    new LiteralDoubleAdditiveGroupOps(lhs)
}


trait MultiplicativeSemigroupSyntax {
  implicit def multiplicativeSemigroupOps[A: MultiplicativeSemigroup](a: A): MultiplicativeSemigroupOps[A] =
    new MultiplicativeSemigroupOps(a)
  implicit def literalIntMultiplicativeSemigroupOps(lhs: Int): LiteralIntMultiplicativeSemigroupOps =
    new LiteralIntMultiplicativeSemigroupOps(lhs)
  implicit def literalLongMultiplicativeSemigroupOps(lhs: Long): LiteralLongMultiplicativeSemigroupOps =
    new LiteralLongMultiplicativeSemigroupOps(lhs)
  implicit def literalDoubleMultiplicativeSemigroupOps(lhs: Double): LiteralDoubleMultiplicativeSemigroupOps =
    new LiteralDoubleMultiplicativeSemigroupOps(lhs)
}

// trait MultiplicativeSemigroupSyntax {
//   extension [A](lhs: A)(using ms: MultiplicativeSemigroup[A])
//     @targetName("times")
//     infix def *(rhs: A): A = ms.times(lhs, rhs)
//     @targetName("times")
//     infix def *(rhs: Int)(using ev1: Ring[A]): A = ms.times(lhs, ev1.fromInt(rhs)) //macro Ops.binopWithLift[Int, Ring[A], A]
//     // @targetName("times")
//     // infix def *(rhs: Double)(using ev1: Field[A]): A = ms.times(lhs, ev1.fromDouble(rhs)) //macro Ops.binopWithLift[Double, Field[A], A]
//     // @targetName("times")
//     // infix def *(rhs: Number)(using c: ConvertableFrom[A]): Number = c.toNumber(lhs) * rhs
//
//   extension(lhs: Int)
//     infix def *[A](rhs: A)(implicit ev: Ring[A]): A = ev.times(ev.fromInt(lhs), rhs)
//
//   // implicit def literalIntMultiplicativeSemigroupOps(lhs: Int): LiteralIntMultiplicativeSemigroupOps =
//   //   new LiteralIntMultiplicativeSemigroupOps(lhs)
//   implicit def literalLongMultiplicativeSemigroupOps(lhs: Long): LiteralLongMultiplicativeSemigroupOps =
//     new LiteralLongMultiplicativeSemigroupOps(lhs)
//   implicit def literalDoubleMultiplicativeSemigroupOps(lhs: Double): LiteralDoubleMultiplicativeSemigroupOps =
//     new LiteralDoubleMultiplicativeSemigroupOps(lhs)
// }

trait MultiplicativeMonoidSyntax extends MultiplicativeSemigroupSyntax {
  implicit def multiplicativeMonoidOps[A](a: A)(implicit ev: MultiplicativeMonoid[A]): MultiplicativeMonoidOps[A] =
    new MultiplicativeMonoidOps(a)
}

trait MultiplicativeGroupSyntax extends MultiplicativeMonoidSyntax {
  implicit def multiplicativeGroupOps[A: MultiplicativeGroup](a: A): MultiplicativeGroupOps[A] =
    new MultiplicativeGroupOps(a)
  implicit def literalIntMultiplicativeGroupOps(lhs: Int): LiteralIntMultiplicativeGroupOps =
    new LiteralIntMultiplicativeGroupOps(lhs)
  implicit def literalLongMultiplicativeGroupOps(lhs: Long): LiteralLongMultiplicativeGroupOps =
    new LiteralLongMultiplicativeGroupOps(lhs)
  implicit def literalDoubleMultiplicativeGroupOps(lhs: Double): LiteralDoubleMultiplicativeGroupOps =
    new LiteralDoubleMultiplicativeGroupOps(lhs)
}

// trait MultiplicativeGroupSyntax extends MultiplicativeMonoidSyntax {
//
//   extension [A ](lhs: A)(using mg: MultiplicativeGroup[A])
//     def reciprocal(): A = mg.reciprocal(lhs)
//     infix def /(rhs: A): A = mg.div(lhs, rhs)
//     infix def /(rhs: Int)(implicit ev1: Ring[A]): A = mg.div(lhs, ev1.fromInt(rhs)) //macro Ops.binopWithLift[Int, Ring[A], A]
//     infix def /(rhs: Double)(implicit ev1: Field[A]): A = mg.div(lhs, ev1.fromDouble(rhs)) //macro Ops.binopWithLift[Double, Field[A], A]
//     infix def /(rhs: Number)(implicit c: ConvertableFrom[A]): Number = c.toNumber(lhs) / rhs
//
//   implicit def literalIntMultiplicativeGroupOps(lhs: Int): LiteralIntMultiplicativeGroupOps =
//     new LiteralIntMultiplicativeGroupOps(lhs)
//   implicit def literalLongMultiplicativeGroupOps(lhs: Long): LiteralLongMultiplicativeGroupOps =
//     new LiteralLongMultiplicativeGroupOps(lhs)
//   implicit def literalDoubleMultiplicativeGroupOps(lhs: Double): LiteralDoubleMultiplicativeGroupOps =
//     new LiteralDoubleMultiplicativeGroupOps(lhs)
// }

trait SemiringSyntax extends AdditiveSemigroupSyntax with MultiplicativeSemigroupSyntax {
  implicit def semiringOps[A: Semiring](a: A): SemiringOps[A] = new SemiringOps(a)
  // extension [A](lhs: A)(using sg: Semiring[A])
  //   def pow(rhs: Int): A = sg.pow(lhs, rhs)
  // def **(rhs: Int): A = macro Ops.binop[Int, A]
}

trait RigSyntax extends SemiringSyntax

trait RngSyntax extends SemiringSyntax with AdditiveGroupSyntax

trait RingSyntax extends RngSyntax with RigSyntax

trait GCDRingSyntax extends RingSyntax {
  implicit def gcdRingOps[A: GCDRing](a: A): GCDRingOps[A] = new GCDRingOps(a)
}

trait EuclideanRingSyntax extends GCDRingSyntax {
  // implicit def euclideanRingOps[A: EuclideanRing](a: A): EuclideanRingOps[A] = new EuclideanRingOps(a)
  extension [A](lhs: A)(using er: EuclideanRing[A])
    // def euclideanFunction(): BigInt = macro Ops.unop[BigInt]
    // def equot(rhs: A): A = macro Ops.binop[A, A]
    def emod(rhs: A): A = er.emod(lhs, rhs)
    def equotmod(rhs: A): (A, A) = er.equotmod(lhs, rhs)

    // TODO: This is a bit
    // def equot(rhs: Int): A = macro Ops.binopWithSelfLift[Int, Ring[A], A]
    // def emod(rhs: Int): A = macro Ops.binopWithSelfLift[Int, Ring[A], A]
    // def equotmod(rhs: Int): (A, A) = macro Ops.binopWithSelfLift[Int, Ring[A], (A, A)]
    //
    // def equot(rhs: Double)(implicit ev1: Field[A]): A = macro Ops.binopWithLift[Double, Field[A], A]
    // def emod(rhs: Double)(implicit ev1: Field[A]): A = macro Ops.binopWithLift[Double, Field[A], A]
    // def equotmod(rhs: Double)(implicit ev1: Field[A]): (A, A) = macro Ops.binopWithLift[Double, Field[A], (A, A)]

    /* TODO: move to TruncatedDivision
    def /~(rhs:Number)(implicit c:ConvertableFrom[A]): Number = c.toNumber(lhs) /~ rhs
    def %(rhs:Number)(implicit c:ConvertableFrom[A]): Number = c.toNumber(lhs) % rhs
    def /%(rhs:Number)(implicit c:ConvertableFrom[A]): (Number, Number) = c.toNumber(lhs) /% rhs
    */
  implicit def literalIntEuclideanRingOps(lhs: Int): LiteralIntEuclideanRingOps = new LiteralIntEuclideanRingOps(lhs)
  implicit def literalLongEuclideanRingOps(lhs: Long): LiteralLongEuclideanRingOps = new LiteralLongEuclideanRingOps(
    lhs
  )
  implicit def literalDoubleEuclideanRingOps(lhs: Double): LiteralDoubleEuclideanRingOps =
    new LiteralDoubleEuclideanRingOps(lhs)
}

trait FieldSyntax extends EuclideanRingSyntax with MultiplicativeGroupSyntax

trait NRootSyntax {
  // implicit def nrootOps[A: NRoot](a: A): NRootOps[A] = new NRootOps(a)
  extension [A](lhs: A)(using ev: NRoot[A])
    def nroot(rhs: Int): A = ev.nroot(lhs, rhs)
    def sqrt(): A = ev.sqrt(lhs)
    def fpow(rhs: A): A = ev.fpow(lhs, rhs)

    // TODO: should be macros
    def pow(rhs: Double)(using c: Field[A]): A = ev.fpow(lhs, c.fromDouble(rhs))
    def **(rhs: Double)(using c: Field[A]): A = ev.fpow(lhs, c.fromDouble(rhs))

    def pow(rhs: Number)(using c: ConvertableFrom[A]): Number = c.toNumber(lhs).pow(rhs)
    def **(rhs: Number)(using c: ConvertableFrom[A]): Number = c.toNumber(lhs) ** rhs
}

trait LeftModuleSyntax extends RingSyntax {
  implicit def leftModuleOps[V](v: V): LeftModuleOps[V] = new LeftModuleOps[V](v)
}

trait RightModuleSyntax extends RingSyntax {
  implicit def rightModuleOps[V](v: V): RightModuleOps[V] = new RightModuleOps[V](v)
}

trait CModuleSyntax extends LeftModuleSyntax with RightModuleSyntax

trait VectorSpaceSyntax extends CModuleSyntax with FieldSyntax {
  implicit def vectorSpaceOps[V](v: V): VectorSpaceOps[V] = new VectorSpaceOps[V](v)
}

trait MetricSpaceSyntax extends VectorSpaceSyntax {
  implicit def metricSpaceOps[V](v: V): MetricSpaceOps[V] = new MetricSpaceOps[V](v)
}

trait NormedVectorSpaceSyntax extends MetricSpaceSyntax {
  implicit def normedVectorSpaceOps[V](v: V): NormedVectorSpaceOps[V] = new NormedVectorSpaceOps[V](v)
}

trait InnerProductSpaceSyntax extends VectorSpaceSyntax {
  // implicit def innerProductSpaceOps[V](v: V): InnerProductSpaceOps[V] = new InnerProductSpaceOps[V](v)
  extension [V](lhs: V)
    def dot[F](rhs: V)(using ev: InnerProductSpace[V, F]): F =
      ev.dot(lhs, rhs)
    // def ⋅[F](rhs: V)(implicit ev: InnerProductSpace[V, F]): F =
    //   macro Ops.binopWithEv[V, InnerProductSpace[V, F], F]
}

trait CoordinateSpaceSyntax extends InnerProductSpaceSyntax {
  implicit def coordinateSpaceOps[V](v: V): CoordinateSpaceOps[V] = new CoordinateSpaceOps[V](v)
}

trait TrigSyntax {
  implicit def trigOps[A: Trig](a: A): TrigOps[A] = new TrigOps(a)
}

trait LatticeSyntax {
  implicit def meetOps[A: MeetSemilattice](a: A): MeetOps[A] = new MeetOps(a)
  implicit def joinOps[A: JoinSemilattice](a: A): JoinOps[A] = new JoinOps(a)
}

trait HeytingSyntax {
  implicit def heytingOps[A: Heyting](a: A): HeytingOps[A] = new HeytingOps(a)
}

trait LogicSyntax {
  implicit def logicOps[A: Logic](a: A): LogicOps[A] = new LogicOps(a)
}

trait BoolSyntax extends HeytingSyntax {
  implicit def boolOps[A: Bool](a: A): BoolOps[A] = new BoolOps(a)
}

trait BitStringSyntax {
  implicit def bitStringOps[A: BitString](a: A): BitStringOps[A] = new BitStringOps(a)
}

trait PartialActionSyntax {
  implicit def leftPartialActionOps[G](g: G): LeftPartialActionOps[G] = new LeftPartialActionOps(g)
  implicit def rightPartialActionOps[P](p: P): RightPartialActionOps[P] = new RightPartialActionOps(p)
}

trait ActionSyntax {
  implicit def leftActionOps[G](g: G): LeftActionOps[G] = new LeftActionOps(g)
  implicit def rightActionOps[P](p: P): RightActionOps[P] = new RightActionOps(p)
}

trait IntervalSyntax {
  implicit def groupActionGroupOps[A: Order: AdditiveGroup](a: A): IntervalPointOps[A] =
    new IntervalPointOps(a)
}

trait UnboundSyntax {
  implicit def moduleUnboundOps[F](f: F)(implicit ev: CModule[_, F]): ModuleUnboundOps[F] =
    new ModuleUnboundOps(f)

  implicit def vectorSpaceUnboundOps[F](f: F)(implicit ev: VectorSpace[_, F]): VectorSpaceUnboundOps[F] =
    new VectorSpaceUnboundOps(f)

  implicit def groupActionUnboundOps[G](g: G)(implicit ev: Action[_, G]): ActionUnboundOps[G] =
    new ActionUnboundOps(g)
  implicit def additiveActionUnboundOps[G](g: G)(implicit ev: AdditiveAction[_, G]): AdditiveActionUnboundOps[G] =
    new AdditiveActionUnboundOps(g)
  implicit def multiplicativeActionUnboundOps[G](g: G)(implicit
    ev: MultiplicativeAction[_, G]
  ): MultiplicativeActionUnboundOps[G] =
    new MultiplicativeActionUnboundOps(g)
}

trait TorsorSyntax {
  implicit def torsorPointOps[P](p: P): TorsorPointOps[P] = new TorsorPointOps(p)
}

trait IntegralSyntax extends EuclideanRingSyntax with ConvertableFromSyntax with OrderSyntax with SignedSyntax {
  implicit def integralOps[A: Integral](a: A): IntegralOps[A] = new IntegralOps(a)
}

trait FractionalSyntax
    extends FieldSyntax
    with NRootSyntax
    with ConvertableFromSyntax
    with OrderSyntax
    with SignedSyntax

trait NumericSyntax extends FieldSyntax with NRootSyntax with ConvertableFromSyntax with OrderSyntax with SignedSyntax

trait ConvertableFromSyntax {
  // implicit def convertableOps[A: ConvertableFrom](a: A): ConvertableFromOps[A] = new ConvertableFromOps(a)
  extension [A](lhs: A)(using cf: ConvertableFrom[A])
    override def toString(): String = cf.toString(lhs)
    def toByte(): Byte = cf.toByte(lhs)
    def toShort(): Short = cf.toShort(lhs)
    def toInt(): Int = cf.toInt(lhs)
    def toLong(): Long = cf.toLong(lhs)
    def toFloat(): Float = cf.toFloat(lhs)
    def toDouble(): Double = cf.toDouble(lhs)
    def toBigInt(): BigInt = cf.toBigInt(lhs)
    def toBigDecimal(): BigDecimal = cf.toBigDecimal(lhs)
    def toRational(): Rational = cf.toRational(lhs)
}

// trait LiteralsSyntax {
//   implicit def literals(s: StringContext): Literals = new Literals(s)
//
//   object radix { implicit def radix(s: StringContext): Radix = new Radix(s) }
//   object si { implicit def siLiterals(s: StringContext): SiLiterals = new SiLiterals(s) }
//   object us { implicit def usLiterals(s: StringContext): UsLiterals = new UsLiterals(s) }
//   object eu { implicit def euLiterals(s: StringContext): EuLiterals = new EuLiterals(s) }
// }
//
trait AllSyntax
    // extends LiteralsSyntax
    extends CforSyntax
    with EqSyntax
    with PartialOrderSyntax
    with OrderSyntax
    with SignedSyntax
    with TruncatedDivisionSyntax
    with InvolutionSyntax
    with IsRealSyntax
    with ConvertableFromSyntax
    with SemigroupoidSyntax
    with GroupoidSyntax
    with SemigroupSyntax
    with MonoidSyntax
    with GroupSyntax
    with AdditiveSemigroupSyntax
    with AdditiveMonoidSyntax
    with AdditiveGroupSyntax
    with MultiplicativeSemigroupSyntax
    with MultiplicativeMonoidSyntax
    with MultiplicativeGroupSyntax
    with SemiringSyntax
    with RigSyntax
    with RngSyntax
    with RingSyntax
    with GCDRingSyntax
    with EuclideanRingSyntax
    with FieldSyntax
    with NRootSyntax
    with TrigSyntax
    with IntervalSyntax
    with LeftModuleSyntax
    with RightModuleSyntax
    with CModuleSyntax
    with VectorSpaceSyntax
    with NormedVectorSpaceSyntax
    with InnerProductSpaceSyntax
    with CoordinateSpaceSyntax
    with LatticeSyntax
    with LogicSyntax
    with HeytingSyntax
    with BoolSyntax
    with BitStringSyntax
    with PartialActionSyntax
    with ActionSyntax
    with TorsorSyntax
    with IntegralSyntax
    with FractionalSyntax
    with NumericSyntax
    with IntSyntax
    with LongSyntax
    with DoubleSyntax
    with BigIntSyntax
    with ArraySyntax
    with SeqSyntax
