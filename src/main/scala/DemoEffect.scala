case class DemoEffect[T](f0: Unit => T){
  def map[B](f: T => B): DemoEffect[B] = {
    val step1: T = f0(())
    val step2: B = f(step1)

    DemoEffect.delay(step2)
  }

  def flatMap[B](f: T => DemoEffect[B]): DemoEffect[B] = {
    val step1: T = f0()
    val step2: DemoEffect[B] = DemoEffect.delay(f(step1).f0())

    step2
  }
}

object DemoEffect {
  def delay[T](value: T): DemoEffect[T] = DemoEffect[T](_ => value)
}
