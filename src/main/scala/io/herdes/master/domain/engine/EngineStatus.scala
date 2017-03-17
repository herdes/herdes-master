package io.herdes.master.domain.engine

object EngineStatus extends Enumeration {
  val uninitialized, initializing, running, problematic = Value
}
