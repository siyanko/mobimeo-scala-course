trait Metrics {
  def inc(): DemoEffect[Unit]
}

object SimpleMetrics extends Metrics {
  private var COUNTER = 0

  override def inc(): DemoEffect[Unit] = DemoEffect.delay(
    COUNTER += 1,
    println(s"Log metrics: ${COUNTER}")
  )
}
