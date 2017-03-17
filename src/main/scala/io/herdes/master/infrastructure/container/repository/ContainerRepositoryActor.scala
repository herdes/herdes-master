package io.herdes.master.infrastructure.container.repository

import akka.actor.{Actor, Props}
import io.anydev.shared.NamedActorProps
import io.herdes.master.domain.container.Container
import io.herdes.shared.orient.{DocumentContext, DocumentRepositoryActor}

class ContainerRepositoryActor(implicit ec: DocumentContext[Container]) extends DocumentRepositoryActor[Container]

object ContainerRepositoryActor extends NamedActorProps {
  override implicit val actorClass: Class[_ <: Actor] = classOf[ContainerRepositoryActor]
  override implicit val actorName: String = "container-repository-actor"

  override def props: Props = Props(actorClass)
}
