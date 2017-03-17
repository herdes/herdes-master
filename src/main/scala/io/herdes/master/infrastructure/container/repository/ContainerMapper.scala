package io.herdes.master.infrastructure.container.repository

import com.orientechnologies.orient.core.record.impl.ODocument
import io.herdes.master.domain.container.{Container, ContainerStatus}
import spray.json.{DeserializationException, JsString, JsValue, RootJsonFormat, pimpAny, pimpString}
import spray.json.DefaultJsonProtocol._

object ContainerMapper {
  private implicit val containerStatusJson = ContainerStatusJsonFormat
  private implicit val containerJson = jsonFormat7(Container)
  
  implicit def documentToEntity(ret: ODocument): Container = {
    ret.toJSON.parseJson.convertTo[Container]
  }

  implicit def entityToDocument(container: Container): ODocument = {
    createNewDocument.fromJSON(container.toJson.toString)
  }

  private[container] def createNewDocument = {
    new ODocument("Container")
  }
}

object ContainerStatusJsonFormat extends RootJsonFormat[ContainerStatus.Value] {
  def write(obj: ContainerStatus.Value): JsValue = JsString(obj.toString)

  def read(json: JsValue): ContainerStatus.Value = json match {
    case JsString(str) => ContainerStatus.withName(str)
    case _ => throw DeserializationException("Enum string expected")
  }
}
