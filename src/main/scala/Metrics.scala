import categories.Applicative

trait Metrics[F[_]]{
  def inc(implicit applicative: Applicative[F]): F[Unit]
}

class SimpleMetrics[F[_]] extends Metrics[F] {
  private var COUNTER = 0

  override def inc(implicit applicative: Applicative[F]): F[Unit] = applicative.point(
    COUNTER += 1,
    println(s"Log metrics: ${COUNTER}")
  )
}
