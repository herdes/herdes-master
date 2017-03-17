package io.herdes.master.infrastructure.engine.repository

import com.orientechnologies.orient.core.record.impl.ODocument
import io.herdes.master.domain.engine.{Engine, EngineResources, EngineStatus, EngineType}
import spray.json.DefaultJsonProtocol._
import spray.json._

private[master] object EngineMapper {
  private implicit val engineTypeJson = EngineTypeJsonFormat
  private implicit val engineStatusJson = EngineStatusJsonFormat
  private implicit val engineResourcesJson = jsonFormat4(EngineResources)
  private implicit val engineJson = jsonFormat11(Engine)

  private[master] implicit def documentToEngine(ret: ODocument): Engine = {
    ret.toJSON.parseJson.convertTo[Engine]
  }

  private[master] implicit def engineToDocument(engine: Engine): ODocument = {
    createNewDocument.fromJSON(engine.toJson.toString)
  }

  private[master] def createNewDocument = {
    new ODocument("Engine")
  }
}

object EngineTypeJsonFormat extends RootJsonFormat[EngineType.Value] {
  def write(obj: EngineType.Value): JsValue = JsString(obj.toString)

  def read(json: JsValue): EngineType.Value = json match {
    case JsString(str) => EngineType.withName(str)
    case _ => throw DeserializationException("Enum string expected")
  }
}

object EngineStatusJsonFormat extends RootJsonFormat[EngineStatus.Value] {
  def write(obj: EngineStatus.Value): JsValue = JsString(obj.toString)

  def read(json: JsValue): EngineStatus.Value = json match {
    case JsString(str) => EngineStatus.withName(str)
    case _ => throw DeserializationException("Enum string expected")
  }
}
