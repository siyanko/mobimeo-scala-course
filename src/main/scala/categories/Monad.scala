package categories

trait Monad[F[_]]{
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object Monad {
  implicit class MonadSyntax[F[_], A](fa: F[A]){
    def flatMap[B](f: A => F[B])(implicit monad: Monad[F]): F[B] = monad.flatMap(fa)(f)
  }
}
