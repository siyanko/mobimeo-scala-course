import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    implicit val simpleDB2: Database[IO] = new SimpleDB[IO]

    implicit val simpleHafas2: Hafas[IO] = new SimpleHafas[IO]

    implicit val simpleLogging2: Logging[IO] = new SimpleLogging[IO]

    implicit val simpleMetrics2: Metrics[IO] = new SimpleMetrics[IO]

    BusinessLogic.program[IO].handleErrorWith {
      case th: Throwable =>
        simpleLogging2.printlnInfo(s"ERROR >>> ${th.getMessage}")
    }.as(ExitCode.Success)
  }
}




