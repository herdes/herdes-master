package io.herdes.master.domain.engine

class EngineResourcesRetrievingException() extends RuntimeException {

  override def getMessage: String = {
    "an error occurred during engine retrieving resources"
  }
}
