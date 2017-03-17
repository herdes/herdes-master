package io.herdes.master.domain.engine

class EngineInitializationException(val engine: Engine) extends RuntimeException {

  override def getMessage: String = {
    "an error occurred during engine initialization '" + engine.id + "'"
  }
}
