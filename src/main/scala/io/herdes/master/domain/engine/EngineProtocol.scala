package io.herdes.master.domain.engine

import scala.concurrent.Promise

abstract sealed class EngineProtocol
case class GetEngine(id: String) extends EngineProtocol
case class ListEngines() extends EngineProtocol
case class SaveEngine(engine: Engine) extends EngineProtocol
case class SaveEngineMutation(mutation: EngineMutation) extends EngineProtocol
case class InitializeEngine(id: String, promise: Promise[Engine] = Promise()) extends EngineProtocol
case class DeleteEngine(id: String) extends EngineProtocol
