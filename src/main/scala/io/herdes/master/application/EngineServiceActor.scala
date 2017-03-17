package io.herdes.master.application

import akka.actor.{Actor, ActorLogging}
import akka.pattern.{ask, pipe}
import io.herdes.master.domain.engine.EngineMutation.engineToEngineMutationOps
import io.herdes.master.domain.engine._
import io.herdes.master.infrastructure.engine.repository.EngineRepositoryActor
import io.herdes.master.infrastructure.provisioning.{Provision, ProvisioningActor}
import io.herdes.shared.orient.{DeleteItem, GetItem, ListItems, SaveItem}
import io.scalaberries.akka.ActorFactory

import scala.concurrent.duration._
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

class EngineServiceActor extends Actor with ActorLogging {
  implicit val system = context.system
  implicit val executionContext = system.dispatcher
  private val repositoryActor = EngineRepositoryActor.actor
  private val provisioningActor = ProvisioningActor.actor

  override def receive: Receive = {
    case GetEngine(id) =>
      repositoryActor forward GetItem(id)

    case ListEngines() =>
      repositoryActor forward ListItems()
      val agent = system.actorSelection("akka://herdes-agent@127.0.0.1:25521/user/container-service-actor")
      log.info("Agent {}", agent)
      agent ! "ala"

    case SaveEngineMutation(mutation) =>
      val enginePromise = mutation.id match {
        case Some(id) =>
          getEngine(id).map(engine => engine.mutate(mutation)).future
        case None =>
          Promise[Engine].success(mutation).future
      }

      val origSender = sender
      enginePromise.map(engine => repositoryActor.tell(SaveItem(engine), origSender))

    case InitializeEngine(id, promise) =>
      val initializedFuture = getEngine(id).flatMap(changeStatusInitializing)
      initializedFuture pipeTo sender

      val provisionedFeature = initializedFuture.flatMap(provision).flatMap(changeStatusRunning)
      provisionedFeature.onComplete {
        case Success(engine) =>
          promise.success(engine)
        case Failure(exception: EngineInitializationException) =>
          changeStatusProblematic(exception.engine)
          promise.future
      }

    case DeleteEngine(id) =>
      repositoryActor forward DeleteItem(id)

    case _ =>
  }

  private def getEngine(id: String): Future[Engine] = {
    repositoryActor.ask(GetItem(id))(5 seconds).asInstanceOf[Future[Engine]]
  }

  private def changeStatusInitializing(engine: Engine): Future[Engine] = {
    changeStatus(engine, EngineStatus.initializing)
  }

  private def changeStatusRunning(engine: Engine): Future[Engine] = {
    changeStatus(engine, EngineStatus.running)
  }

  private def changeStatusProblematic(engine: Engine): Future[Engine] = {
    changeStatus(engine, EngineStatus.problematic)
  }

  private def changeStatus(engine: Engine, withStatus: EngineStatus.Value): Future[Engine] = {
    val initializingEngine = engine.copy(status = withStatus)
    repositoryActor.ask(SaveItem(initializingEngine))(5 seconds).asInstanceOf[Future[Engine]]
  }

  private def provision(engine: Engine): Future[Engine] = {
    provisioningActor.ask(Provision(engine))(120 seconds).asInstanceOf[Future[Engine]]
  }
}

object EngineServiceActor extends ActorFactory {
  override implicit val actorClass: Class[_ <: Actor] = classOf[EngineServiceActor]
  override implicit val actorName: String = "engine-service-actor"
}