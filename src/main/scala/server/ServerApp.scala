package server

import cats.data.Kleisli
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.{EntityDecoder, HttpRoutes, Request, Response}
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.dsl.io._
import org.http4s.implicits._
import io.circe.generic.auto._
import org.http4s.circe._
import Domain.{Subscription, SubscriptionRequest, PutRequest}

import java.util.UUID
import scala.concurrent.ExecutionContext.global

object ServerApp extends IOApp {

  implicit val decoderPost: EntityDecoder[IO, SubscriptionRequest] = jsonOf[IO, SubscriptionRequest]
  implicit val decoderPut: EntityDecoder[IO, PutRequest] = jsonOf[IO, PutRequest]

  override def run(args: List[String]): IO[ExitCode] = {
    var storage: Map[String, Subscription] = Map.empty

    def existsId(id: String, request: Either[String, PutRequest]): Either[String, PutRequest] = {
      if (!storage.contains(id))
        Left(s"The subscription with id $id does not exist")
      else
        request
    }

    val helloWorldService: Kleisli[IO, Request[IO], Response[IO]] = HttpRoutes.of[IO] {
      case GET -> Root / "hello" / name =>
        Ok(s"Hello, $name.")
      case DELETE -> Root / "hello" / name =>
        Ok(s"No hello for $name")

      case req @ POST -> Root / "subscriptions" => for {
        request <- req.as[SubscriptionRequest]
        maybeValidRequest = SubscriptionRequest.validate(request)
        _ <- IO(println(maybeValidRequest))
        result <- maybeValidRequest match {
          case None => BadRequest("Invalid body")
          case Some(request) =>
            val id = UUID.randomUUID().toString
            val subscription = Subscription(id, request.origin, request.destination, request.label)
            storage = Map(id -> subscription) ++ storage
            println(storage)
            Ok(s"""{"id": $id}""")
        }
      } yield result

      case req @ PUT -> Root / "subscriptions" / id => for {
        request <- req.as[PutRequest]
        maybeValidRequest = PutRequest.validate(request)
        _ <- IO(println(maybeValidRequest))
        request = existsId(id, maybeValidRequest)
        result <- request match {
          case Left(value) => BadRequest(value)
          case Right(request) =>
            val subscription = Subscription(id, request.origin, request.destination, request.label)
            storage = storage ++ Map(id -> subscription)
            println(storage)
            Ok(s"""{"id": $id}""")
        }
      } yield result
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

object Domain {
  case class SubscriptionRequest(origin: String, destination: String, label: String)
  case class PutRequest(origin: String, destination: String, label: String)

  case class Subscription(id: String, origin: String, destination: String, label: String)

  object SubscriptionRequest {
    def validate(request: SubscriptionRequest): Option[SubscriptionRequest] = if (request.origin.nonEmpty && request.destination.nonEmpty && request.label.nonEmpty)
      Some(request) else None
  }

  object PutRequest {
    def validate(request: PutRequest): Either[String, PutRequest] =
      if (request.origin.nonEmpty && request.destination.nonEmpty && request.label.nonEmpty)
        Right(request)
      else
        Left("Validation failed, missing attribute")
  }
}
