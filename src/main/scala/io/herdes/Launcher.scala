package io.herdes

import akka.actor.ActorSystem

object Launcher extends App {
  implicit val system = ActorSystem("herdes-master")
  val topSupervisor = TopSupervisor.actor
}
