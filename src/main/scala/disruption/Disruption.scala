package disruption

sealed trait Disruption {
  def showMe: String
}

object Disruption {
  def showMeDisruption(a: Disruption): String = a match {
    case Delay(5) => "DELAY"
    case _: Delay => s"delay"
    //      case Cancellation => "cancellation"
    case _: PlannedDisruption => "planned disruption"
    case _ => "I don't know"
  }
}

case class Delay(delayInMin: Int) extends Disruption {
  override def showMe: String = "delay"
}

// 1 - 59 min
object Delay {
  def fromInt(i: Int): Option[Delay] = if (i > 0 && i < 60) Some(Delay(i)) else None
}

case object Cancellation extends Disruption {
  override def showMe: String = "cancellation"
  //goes here
}


case class PlannedDisruption(message: String) extends Disruption {
  override def showMe: String = "planned disruption"
}


case class FakeDisruption(msg: String) extends Disruption {
  override def showMe: String = "I'm fake"
}


