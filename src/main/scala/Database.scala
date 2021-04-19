trait Database {
  def fetchingDataFromDB: DemoEffect[Unit]
}

object SimpleDB extends Database{
  override def fetchingDataFromDB: DemoEffect[Unit] = DemoEffect.delay(
    println("Fetching data from DB")
  )
}
