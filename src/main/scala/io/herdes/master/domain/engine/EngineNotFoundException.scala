package io.herdes.master.domain.engine

class EngineNotFoundException(id: String) extends RuntimeException {

  override def getMessage: String = {
    "there is no engine with id '" + id + "'"
  }
}
