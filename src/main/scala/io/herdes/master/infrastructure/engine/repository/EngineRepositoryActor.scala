package io.herdes.master.infrastructure.engine.repository

import akka.actor.{Actor, Props}
import io.herdes.master.domain.engine.Engine
import io.herdes.shared.orient.{DocumentContext, DocumentRepositoryActor}
import io.scalaberries.akka.ActorFactory

class EngineRepositoryActor(implicit ec: DocumentContext[Engine]) extends DocumentRepositoryActor[Engine]

object EngineRepositoryActor extends ActorFactory {
  override implicit val actorClass: Class[_ <: Actor] = classOf[EngineRepositoryActor]
  override implicit val actorName: String = "engine-repository-actor"
  override def baseProps: Props = Props(actorClass, EngineContext)
}