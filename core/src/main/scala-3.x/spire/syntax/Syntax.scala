package spire
package syntax

import spire.NoImplicit
import spire.algebra._
import spire.algebra.lattice._
import spire.algebra.partial._
import spire.math._
import spire.macros.Syntax
import spire.syntax.std._
import scala.annotation.nowarn
import scala.annotation.targetName

trait EqSyntax {
  implicit def eqOps[A: Eq](a: A): EqOps[A] = new EqOps(a)
}

trait PartialOrderSyntax extends EqSyntax {
  extension [A](lhs: A)(using po: PartialOrder[A])
  // def >(rhs: A): Boolean = macro Ops.binop[A, Boolean]
    infix def >=(rhs: A): Boolean = rhs >= lhs
    infix def <(rhs: A): Boolean = rhs < lhs
  // def <=(rhs: A): Boolean = macro Ops.binop[A, Boolean]
  //
  // def partialCompare(rhs: A): Double = macro Ops.binop[A, Double]
  // def tryCompare(rhs: A): Option[Int] = macro Ops.binop[A, Option[Int]]
  // def pmin(rhs: A): Option[A] = macro Ops.binop[A, A]
  // def pmax(rhs: A): Option[A] = macro Ops.binop[A, A]
  //
  // def >(rhs: Int)(implicit ev1: Ring[A]): Boolean = macro Ops.binopWithLift[Int, Ring[A], A]
  // def >=(rhs: Int)(implicit ev1: Ring[A]): Boolean = macro Ops.binopWithLift[Int, Ring[A], A]
  // def <(rhs: Int)(implicit ev1: Ring[A]): Boolean = macro Ops.binopWithLift[Int, Ring[A], A]
  // def <=(rhs: Int)(implicit ev1: Ring[A]): Boolean = macro Ops.binopWithLift[Int, Ring[A], A]
  //
  // def >(rhs: Double)(implicit ev1: Field[A]): Boolean = macro Ops.binopWithLift[Int, Field[A], A]
  // def >=(rhs: Double)(implicit ev1: Field[A]): Boolean = macro Ops.binopWithLift[Int, Field[A], A]
  // def <(rhs: Double)(implicit ev1: Field[A]): Boolean = macro Ops.binopWithLift[Int, Field[A], A]
  // def <=(rhs: Double)(implicit ev1: Field[A]): Boolean = macro Ops.binopWithLift[Int, Field[A], A]

    infix def >(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) > rhs
    infix def >=(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) >= rhs
    infix def <(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) < rhs
    infix def <=(rhs: Number)(implicit c: ConvertableFrom[A]): Boolean = c.toNumber(lhs) <= rhs
}

trait OrderSyntax extends PartialOrderSyntax {
  implicit def orderOps[A: Order](a: A): OrderOps[A] = new OrderOps(a)
  implicit def literalIntOrderOps(lhs: Int): LiteralIntOrderOps = new LiteralIntOrderOps(lhs)
  implicit def literalLongOrderOps(lhs: Long): LiteralLongOrderOps = new LiteralLongOrderOps(lhs)
  implicit def literalDoubleOrderOps(lhs: Double): LiteralDoubleOrderOps = new LiteralDoubleOrderOps(lhs)
}

trait SignedSyntax extends OrderSyntax {
  // implicit def signedOps[A: Signed](a: A): SignedOps[A] = new SignedOps(a)
  extension [A](a: A)(using s: Signed[A])
    def isSignZero(): Boolean = s.isSignZero(a)
// final class SignedOps[A: Signed](lhs: A) {
    def abs(): A = s.abs(a)
  // def sign(): Sign = macro Ops.unop[Sign]
    def signum(): Int = s.signum(a)
  //
  // def isSignZero(): Boolean = macro Ops.unop[Boolean]
  // def isSignPositive(): Boolean = macro Ops.unop[Boolean]
    def isSignNegative(): Boolean = s.isSignNegative(a)
  //
  // def isSignNonZero(): Boolean = macro Ops.unop[Boolean]
  // def isSignNonPositive(): Boolean = macro Ops.unop[Boolean]
  // def isSignNonNegative(): Boolean = macro Ops.unop[Boolean]
// }
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
    infix def +(rhs: A): A = as.plus(lhs, rhs)
    @targetName("plus")
    infix def ^+(rhs: A): A = as.plus(lhs, rhs)
    // def +(rhs: Int)(implicit ev1: Ring[A]): A = ??? //macro Ops.binopWithLift[Int, Ring[A], A]
    // def +(rhs: Double)(implicit ev1: Field[A]): A = ??? //macro Ops.binopWithLift[Double, Field[A], A]
    def +(rhs: Number)(using c: ConvertableFrom[A]): Number = c.toNumber(lhs) + rhs

  implicit def literalIntAdditiveSemigroupOps(lhs: Int): LiteralIntAdditiveSemigroupOps =
    new LiteralIntAdditiveSemigroupOps(lhs)
  implicit def literalLongAdditiveSemigroupOps(lhs: Long): LiteralLongAdditiveSemigroupOps =
    new LiteralLongAdditiveSemigroupOps(lhs)
  implicit def literalDoubleAdditiveSemigroupOps(lhs: Double): LiteralDoubleAdditiveSemigroupOps =
    new LiteralDoubleAdditiveSemigroupOps(lhs)
}

trait AdditiveMonoidSyntax extends AdditiveSemigroupSyntax {
  implicit def additiveMonoidOps[A](a: A)(implicit ev: AdditiveMonoid[A]): AdditiveMonoidOps[A] = new AdditiveMonoidOps(
    a
  )
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
  extension [A](lhs: A)(using ms: MultiplicativeSemigroup[A])
    @targetName("times")
    infix def *(rhs: A): A = ms.times(lhs, rhs)
    @targetName("times")
    infix def *(rhs: Int)(implicit ev1: Ring[A]): A = ms.times(lhs, ev1.fromInt(rhs)) //macro Ops.binopWithLift[Int, Ring[A], A]
    @targetName("times")
    infix def *(rhs: Double)(implicit ev1: Field[A]): A = ms.times(lhs, ev1.fromDouble(rhs)) //macro Ops.binopWithLift[Double, Field[A], A]
    @targetName("times")
    infix def *(rhs: Number)(implicit c: ConvertableFrom[A]): Number = c.toNumber(lhs) * rhs

  implicit def literalIntMultiplicativeSemigroupOps(lhs: Int): LiteralIntMultiplicativeSemigroupOps =
    new LiteralIntMultiplicativeSemigroupOps(lhs)
  implicit def literalLongMultiplicativeSemigroupOps(lhs: Long): LiteralLongMultiplicativeSemigroupOps =
    new LiteralLongMultiplicativeSemigroupOps(lhs)
  implicit def literalDoubleMultiplicativeSemigroupOps(lhs: Double): LiteralDoubleMultiplicativeSemigroupOps =
    new LiteralDoubleMultiplicativeSemigroupOps(lhs)
}

trait MultiplicativeMonoidSyntax extends MultiplicativeSemigroupSyntax {
  implicit def multiplicativeMonoidOps[A](a: A)(implicit ev: MultiplicativeMonoid[A]): MultiplicativeMonoidOps[A] =
    new MultiplicativeMonoidOps(a)
}

trait MultiplicativeGroupSyntax extends MultiplicativeMonoidSyntax {
  extension [A](lhs: A)(using mg: MultiplicativeGroup[A])
    def reciprocal(): A = mg.reciprocal(lhs)
    def /(rhs: A): A = mg.div(lhs, rhs)
    def /(rhs: Int)(implicit ev1: Ring[A]): A = mg.div(lhs, ev1.fromInt(rhs)) //macro Ops.binopWithLift[Int, Ring[A], A]
    def /(rhs: Double)(implicit ev1: Field[A]): A = mg.div(lhs, ev1.fromDouble(rhs)) //macro Ops.binopWithLift[Double, Field[A], A]
    def /(rhs: Number)(implicit c: ConvertableFrom[A]): Number = c.toNumber(lhs) / rhs

  implicit def literalIntMultiplicativeGroupOps(lhs: Int): LiteralIntMultiplicativeGroupOps =
    new LiteralIntMultiplicativeGroupOps(lhs)
  implicit def literalLongMultiplicativeGroupOps(lhs: Long): LiteralLongMultiplicativeGroupOps =
    new LiteralLongMultiplicativeGroupOps(lhs)
  implicit def literalDoubleMultiplicativeGroupOps(lhs: Double): LiteralDoubleMultiplicativeGroupOps =
    new LiteralDoubleMultiplicativeGroupOps(lhs)
}

trait SemiringSyntax extends AdditiveSemigroupSyntax with MultiplicativeSemigroupSyntax {
  implicit def semiringOps[A: Semiring](a: A): SemiringOps[A] = new SemiringOps(a)
  // extension [A](lhs: A)(using sg: Semiring[A])
  //   def pow(rhs: Int): A = ??? //macro Ops.binop[Int, A]
  // def **(rhs: Int): A = macro Ops.binop[Int, A]
}

trait RigSyntax extends SemiringSyntax

trait RngSyntax extends SemiringSyntax with AdditiveGroupSyntax

trait RingSyntax extends RngSyntax with RigSyntax

trait GCDRingSyntax extends RingSyntax {
  implicit def gcdRingOps[A: GCDRing](a: A): GCDRingOps[A] = new GCDRingOps(a)
}

trait EuclideanRingSyntax extends GCDRingSyntax {
  implicit def euclideanRingOps[A: EuclideanRing](a: A): EuclideanRingOps[A] = new EuclideanRingOps(a)
  implicit def literalIntEuclideanRingOps(lhs: Int): LiteralIntEuclideanRingOps = new LiteralIntEuclideanRingOps(lhs)
  implicit def literalLongEuclideanRingOps(lhs: Long): LiteralLongEuclideanRingOps = new LiteralLongEuclideanRingOps(
    lhs
  )
  implicit def literalDoubleEuclideanRingOps(lhs: Double): LiteralDoubleEuclideanRingOps =
    new LiteralDoubleEuclideanRingOps(lhs)
}

trait FieldSyntax extends EuclideanRingSyntax with MultiplicativeGroupSyntax

trait NRootSyntax {
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
  implicit def innerProductSpaceOps[V](v: V): InnerProductSpaceOps[V] = new InnerProductSpaceOps[V](v)
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
  implicit def convertableOps[A: ConvertableFrom](a: A): ConvertableFromOps[A] = new ConvertableFromOps(a)
}

trait LiteralsSyntax {
  implicit def literals(s: StringContext): Literals = new Literals(s)

  object radix { implicit def radix(s: StringContext): Radix = new Radix(s) }
  object si { implicit def siLiterals(s: StringContext): SiLiterals = new SiLiterals(s) }
  object us { implicit def usLiterals(s: StringContext): UsLiterals = new UsLiterals(s) }
  object eu { implicit def euLiterals(s: StringContext): EuLiterals = new EuLiterals(s) }
}

trait AllSyntax
    extends LiteralsSyntax
    with CforSyntax
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
