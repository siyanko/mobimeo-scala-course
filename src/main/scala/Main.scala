
object Main {
  def main(args: Array[String]): Unit = {
    // call at end of the world
    BusinessLogic.program(SimpleDB, SimpleHafas, SimpleLogging, SimpleMetrics).f0()
  }
}




