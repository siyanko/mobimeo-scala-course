import cats.Applicative

trait Database[F[_]] {
  def fetchingDataFromDB(implicit applicative: Applicative[F]): F[Unit]
}

class SimpleDB[F[_]] extends Database[F]{
  override def fetchingDataFromDB(implicit applicative: Applicative[F]): F[Unit] = applicative.point(
//    println("Fetching data from DB")
    throw new Exception("Databse is down")
  )
}
