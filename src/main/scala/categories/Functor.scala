package categories

trait Functor[F[_]]{
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit class FunctorSyntax[F[_], A](fa: F[A]){
    def map[B](f: A => B)(implicit functor: Functor[F]): F[B] = functor.map(fa)(f)
  }
}
