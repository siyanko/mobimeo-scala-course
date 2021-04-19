trait Hafas {
  def fetchingDataFromHafas: DemoEffect[Unit]
}

object SimpleHafas extends Hafas {
  override def fetchingDataFromHafas: DemoEffect[Unit] = DemoEffect.delay(
    println("Fetching data from HAFAS")
  )
}
