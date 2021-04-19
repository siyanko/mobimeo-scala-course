object BusinessLogic {
  def program(db: Database, hafas: Hafas, logging: Logging, metrics: Metrics): DemoEffect[Unit] = for {
    _ <- metrics.inc()
    _ <- logging.printlnInfo("Starting execution")
    _ <- db.fetchingDataFromDB
    _ <- metrics.inc()
    _ = println("Going to call HAFAS")
    _ <- hafas.fetchingDataFromHafas
    _ <- logging.printlnInfo("Executed successfully")
    _ <- metrics.inc()
  } yield ()
}
