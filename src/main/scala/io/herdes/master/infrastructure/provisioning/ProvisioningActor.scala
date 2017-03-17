package io.herdes.master.infrastructure.provisioning

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe
import io.herdes.master.domain.engine.{Engine, EngineInitializationException}
import io.herdes.master.infrastructure.config.ProvisioningConfigManager
import io.scalaberries.akka.ActorFactory

import scala.concurrent.Promise
import scala.sys.process._

class ProvisioningActor extends Actor with ActorLogging {
  implicit val system = context.system
  implicit val executionContext = system.dispatcher

  override def receive: Receive = {
    case Provision(engine) =>
      log.info("Provisioning engine {}", engine)
      val user = engine.user
      val password = engine.password
      val dir = ProvisioningConfigManager.location
      val command = s"""ansible-playbook -i $dir/inventory $dir/docker-install.yml -e ansible_ssh_user='$user' -e ansible_ssh_pass='$password' -e ansible_become_pass='$password'"""
      val promise: Promise[Engine] = Promise[Engine]
      promise.future pipeTo sender

      try {
        log.info("executing command {}", command)
        log.info(command.!!)
        promise.success(engine)
      } catch {
        case e: Throwable =>
          log.info("command exception {}", e)
          promise.failure(new EngineInitializationException(engine))
      }

    case GetResources(engine) =>

  }
}

object ProvisioningActor extends ActorFactory {
  override implicit val actorClass: Class[_ <: Actor] = classOf[ProvisioningActor]
  override implicit val actorName: String = "engine-provisioning-actor"
}
