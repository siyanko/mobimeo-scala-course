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

object Delay {
  def apply(): Delay = Delay(5)
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


