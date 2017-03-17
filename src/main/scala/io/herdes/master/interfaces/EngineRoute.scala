package io.herdes.master.interfaces

import akka.actor.ActorRef
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{PathMatchers, Route}
import akka.pattern.ask
import ch.megard.akka.http.cors.{CorsDirectives, CorsSettings}
import io.herdes.master.domain.engine._
import io.herdes.master.interfaces.EngineRouteMapper._
import spray.json.DefaultJsonProtocol._

import scala.collection.immutable.Seq
import scala.concurrent.Future
import scala.concurrent.duration._

object EngineRoute {
  
  def route(engineServiceActor: ActorRef): Route = {
    implicit val engineMutationDTOJson = jsonFormat6(EngineMutationDTO)
    implicit val engineDTOJson = jsonFormat10(EngineDTO)
    val settings = CorsSettings.defaultSettings.copy(allowedMethods = Seq(GET, POST, PUT, HEAD, DELETE, OPTIONS))

    CorsDirectives.cors(settings) {
      pathPrefix("engine") {
        get {
          pathPrefix(PathMatchers.Segment) {
            id => {
              val response: Future[Engine] = sendProtocolMessage(engineServiceActor, GetEngine(id))
              onSuccess(response)(r => complete(ToResponseMarshallable[EngineDTO](r)))
            }
          } ~ {
            val response: Future[List[Engine]] = sendProtocolMessage(engineServiceActor, ListEngines())
            onSuccess(response)(r => complete(ToResponseMarshallable[List[EngineDTO]](r)))
          }
        } ~
        post {
          entity(as[EngineMutationDTO]) { engineMutationDTO =>
            val response: Future[Engine] = sendProtocolMessage(engineServiceActor, SaveEngineMutation(engineMutationDTO))
            onSuccess(response)(r => complete(ToResponseMarshallable[EngineDTO](r)))
          }
        } ~
        put {
          pathPrefix(PathMatchers.Segment) {
            id => {
              pathPrefix("provision") {
                val response: Future[Engine] = sendProtocolMessage(engineServiceActor, InitializeEngine(id))
                onSuccess(response)(r => complete(ToResponseMarshallable[EngineDTO](r)))
              }
            }
          } ~ {
            entity(as[EngineMutationDTO]) { engineMutationDTO =>
              val response: Future[Engine] = sendProtocolMessage(engineServiceActor, SaveEngineMutation(engineMutationDTO))
              onSuccess(response)(r => complete(ToResponseMarshallable[EngineDTO](r)))
            }
          }
        } ~
        delete {
          pathPrefix(PathMatchers.Segment) {
            id => {
              val response: Future[Engine] = sendProtocolMessage(engineServiceActor, DeleteEngine(id))
              onSuccess(response)(r => complete(ToResponseMarshallable[EngineDTO](r)))
            }
          }
        }
      }
    }
  }

  private def sendProtocolMessage[T](engineServiceActor: ActorRef, message: EngineProtocol): Future[T] = {
    engineServiceActor.ask(message)(5 seconds).asInstanceOf[Future[T]]
  }
}
