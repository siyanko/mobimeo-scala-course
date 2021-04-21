import categories.{Applicative, Functor, Monad}
import categories.Functor._
import categories.Monad._

object BusinessLogic {
  def program[F[_]](db: Database[F],
                    hafas: Hafas[F],
                    logging: Logging[F],
                    metrics: Metrics[F])(implicit functor: Functor[F], monad: Monad[F], applicative: Applicative[F]): F[Unit] = for {
    _ <- metrics.inc
    _ <- logging.printlnInfo("Starting execution")
    _ <- db.fetchingDataFromDB
    _ <- metrics.inc
    _ = println("Going to call HAFAS")
    _ <- hafas.fetchingDataFromHafas
    _ <- logging.printlnInfo("Executed successfully")
    _ <- metrics.inc
  } yield ()

  //flatMap => Monad
}


