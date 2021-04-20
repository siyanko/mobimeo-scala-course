package categories

trait Applicative[F[_]]{
  def point[A](a: A): F[A]
}
