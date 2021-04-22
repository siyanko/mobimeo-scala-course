import cats.Applicative

trait Hafas[F[_]] {
  def fetchingDataFromHafas(implicit applicative: Applicative[F]): F[Unit]
}

class SimpleHafas[F[_]] extends Hafas[F] {
  override def fetchingDataFromHafas(implicit applicative: Applicative[F]): F[Unit] = applicative.point(
    throw new Exception("Hafas has some internall error")
  )
}
