import cats.{Applicative, Functor, Monad, MonadError}
import cats.implicits._

object BusinessLogic {

  def saveDbCall[F[_]](implicit db: Database[F], logging: Logging[F], monadError: MonadError[F, Throwable]): F[Unit] =
    monadError.recoverWith(db.fetchingDataFromDB) { // don't fail here
      th: Throwable => logging.printlnInfo(s"ERROR <<< ${th.getMessage}")
    }

  def program[F[_]](implicit db: Database[F],
                    hafas: Hafas[F],
                    logging: Logging[F],
                    metrics: Metrics[F],
                    functor: Functor[F],
                    monad: Monad[F],
                    applicative: Applicative[F],
                    monadError: MonadError[F, Throwable]): F[Unit] = for {
    _ <- metrics.inc
    _ <- logging.printlnInfo("Starting execution")
    _ <- saveDbCall
    _ <- metrics.inc
    _ = println("Going to call HAFAS")
    _ <- hafas.fetchingDataFromHafas
    _ <- logging.printlnInfo("Executed successfully")
    _ <- metrics.inc
  } yield ()
}

