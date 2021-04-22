import cats.Applicative

trait Logging[F[_]] {
  def printlnInfo(message: String)(implicit applicative: Applicative[F]): F[Unit]
}

class SimpleLogging[F[_]] extends Logging[F] {
  override def printlnInfo(message: String)(implicit applicative: Applicative[F]): F[Unit] = applicative.point(println(s"Log message: $message"))
}
