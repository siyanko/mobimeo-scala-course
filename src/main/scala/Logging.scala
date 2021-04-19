trait Logging {
  def printlnInfo(message: String): DemoEffect[Unit]
}

object SimpleLogging extends Logging {
  override def printlnInfo(message: String): DemoEffect[Unit] = DemoEffect.delay(println(s"Log message: $message"))
}
