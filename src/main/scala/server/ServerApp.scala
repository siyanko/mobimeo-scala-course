package server
import cats.data.Kleisli
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.{HttpRoutes, Request, Response}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.dsl.io._
import org.http4s.implicits._

import scala.concurrent.ExecutionContext.global

object ServerApp extends IOApp{
  override def run(args: List[String]): IO[ExitCode] = {

    val helloWorldService: Kleisli[IO, Request[IO], Response[IO]] = HttpRoutes.of[IO] {
      case GET -> Root / "hello" / name =>
        Ok(s"Hello, $name.")
      case DELETE -> Root / "hello" / name =>
        Ok(s"No hello for $name")
    }.orNotFound

    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(helloWorldService)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }

}
