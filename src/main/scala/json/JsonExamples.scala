package json
import io.circe.{Decoder, DecodingFailure}
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._
import json.JsonExamples.ConnectionAlarmRequest.myDecoder
import json.JsonExamples.SubscriptionLabel._
import json.JsonExamples.Origin._
import json.JsonExamples.Destination._

object JsonExamples {

  case class SubscriptionLabel(value: String) extends AnyVal
  object SubscriptionLabel {
    def fromString(s: String): Either[String, SubscriptionLabel] = if(s.isEmpty) Left("Empty label") else Right(SubscriptionLabel(s))

    // Either[DecodingFailure, String] =>  Either[DecodingFailure, SubscriptionLabel]
    implicit val decoder: Decoder[SubscriptionLabel] = Decoder.decodeString.emap(fromString)
  }
  case class Origin(value: String) extends AnyVal
  object Origin{
    implicit val decoder: Decoder[Origin] = Decoder.decodeString.map(Origin.apply)
  }

  case class DelayThreshold(value: Int) extends AnyVal
  object DelayThreshold {
    def fromInt(i: Int): Either[String, DelayThreshold] =
      // depending on what is required more checks can be added here
      if(i > 59 | i < 1) Left("out of range") else Right(DelayThreshold(i))

    implicit val decoder: Decoder[DelayThreshold] = Decoder.decodeInt.emap(fromInt)
  }

  case class Destination(value: String) extends AnyVal
  object Destination{
    implicit val decoder: Decoder[Destination] = Decoder.decodeString.map(Destination.apply)
  }

  case class ConnectionAlarmRequest(label2: SubscriptionLabel, origin: Origin, destination: Destination, optDays: List[String],
                                    delayThreshold: DelayThreshold) // 1 - 59 min
  object ConnectionAlarmRequest{
    implicit val myDecoder: Decoder[ConnectionAlarmRequest] = Decoder.instance(hCursor => for {
      label2 <- hCursor.get[SubscriptionLabel]("label")
      origin <- hCursor.get[Origin]("origin")
      destination <- hCursor.get[Destination]("destination")
      optDays <- hCursor.get[List[String]]("optDays")
      delayThreshold <- hCursor.get[DelayThreshold]("delayThreshold")
    } yield ConnectionAlarmRequest(label2, origin, destination, optDays, delayThreshold))
  }

  def main(args: Array[String]): Unit = {
//    val request = ConnectionAlarmRequest("Test Subscription", "Alexanderplatz", "Hbf", List("mon", "tue", "wed"), None)

//    println(request.asJson.noSpaces)

    val jsonStrRightDelay =
      s"""{"label":"bla","destination":"Hbf", "origin":"Alexanderplatz","optDays":["mon","tue","wed"],"delayThreshold": 59}""".stripMargin
    println(parse(jsonStrRightDelay).flatMap(json => json.as[ConnectionAlarmRequest]))

    val jsonStrLeftDelay =
      s"""{"label":"bla","destination":"Hbf", "origin":"Alexanderplatz","optDays":["mon","tue","wed"],"delayThreshold": 0}""".stripMargin
    println(parse(jsonStrLeftDelay).flatMap(json => json.as[ConnectionAlarmRequest]))
  }

}
