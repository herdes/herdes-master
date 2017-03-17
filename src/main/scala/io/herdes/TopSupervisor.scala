package io.herdes

import akka.actor.SupervisorStrategy.{Escalate, Restart}
import akka.actor.{Actor, AllDeadLetters, OneForOneStrategy}
import io.herdes.master.infrastructure.endpoint.HttpServerActor
import io.scalaberries.akka.{ActorEmptyReceive, ActorFactory}

class TopSupervisor extends Actor with ActorEmptyReceive {
  private val system = context.system
  HttpServerActor.actor

  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => Restart
    case _ => Escalate
  }

  override def preStart(): Unit = {
    system.eventStream.subscribe(self, classOf[AllDeadLetters])
  }
}

object TopSupervisor extends ActorFactory {
  override implicit val actorClass: Class[_ <: Actor] = classOf[TopSupervisor]
  override implicit val actorName: String = "top-supervisor-actor"
}