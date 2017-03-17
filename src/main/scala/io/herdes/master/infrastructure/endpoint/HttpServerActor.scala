package io.herdes.master.infrastructure.endpoint

import akka.actor.Actor
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import io.herdes.master.application.EngineServiceActor
import io.herdes.master.infrastructure.config.HttpConfigManager
import io.herdes.master.interfaces.Route
import io.scalaberries.akka.{ActorEmptyReceive, ActorFactory}

class HttpServerActor extends Actor with ActorEmptyReceive {

  private val httpHost = HttpConfigManager.httpHost
  private val httpPort = HttpConfigManager.httpPort

  implicit val system = context.system
  implicit val materializer = ActorMaterializer()
  private val engineServiceActor = EngineServiceActor.actor

  override def preStart(): Unit = {
    val route = Route.route(engineServiceActor)
    Http().bindAndHandle(route, httpHost, httpPort)
  }
}

object HttpServerActor extends ActorFactory {
  override implicit val actorClass: Class[_ <: Actor] = classOf[HttpServerActor]
  override implicit val actorName: String = "http-server-actor"
}