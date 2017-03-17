package io.herdes.master.interfaces

import akka.actor.ActorRef
import akka.http.scaladsl.server.{Route, RouteConcatenation}

object Route extends RouteConcatenation {
  def route(engineServiceActor: ActorRef): Route = {
    EngineRoute.route(engineServiceActor)
  }
}
