import categories.{Applicative, Functor, Monad}

object Main {
  def main(args: Array[String]): Unit = {

    implicit val functorDemoEffect: Functor[DemoEffect] = new Functor[DemoEffect] {
      override def map[A, B](fa: DemoEffect[A])(f: A => B): DemoEffect[B] = fa.map(f)
    }

    implicit val functorDemoEffect2: Functor[Option] = new Functor[Option] {
      override def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa.map(f)
    }

    implicit val monadDemoEffect: Monad[DemoEffect] = new Monad[DemoEffect] {
      override def flatMap[A, B](fa: DemoEffect[A])(f: A => DemoEffect[B]): DemoEffect[B] = fa.flatMap(f)
    }

    implicit val monadDemoEffect2: Monad[Option] = new Monad[Option] {
      override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa.flatMap(f)
    }

    implicit val applicativeDemoEffect: Applicative[DemoEffect] = new Applicative[DemoEffect] {
      override def point[A](a: A): DemoEffect[A] = DemoEffect.delay(a)
    }

    implicit val applicativeDemoEffect2: Applicative[Option] = new Applicative[Option] {
      override def point[A](a: A): Option[A] = Option(a)
    }

    val simpleDB: Database[DemoEffect] = new SimpleDB[DemoEffect]
    implicit val simpleDB2: Database[Option] = new SimpleDB[Option]

    val simpleHafas: Hafas[DemoEffect] = new SimpleHafas[DemoEffect]
    implicit val simpleHafas2: Hafas[Option] = new SimpleHafas[Option]

    val simpleLogging: Logging[DemoEffect] = new SimpleLogging[DemoEffect]
    implicit val simpleLogging2: Logging[Option] = new SimpleLogging[Option]

    val simpleMetrics: Metrics[DemoEffect] = new SimpleMetrics[DemoEffect]
    implicit val simpleMetrics2: Metrics[Option] = new SimpleMetrics[Option]

    // call at end of the world
//    BusinessLogic.program[DemoEffect](simpleDB, simpleHafas, simpleLogging, simpleMetrics).f0()

    // BusinessLogic.program[Option](simpleDB2, simpleHafas2, simpleLogging2, simpleMetrics2).get

    BusinessLogic.program[Option].get
  }
}




