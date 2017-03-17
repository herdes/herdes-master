package io.herdes.master.domain.engine

import java.time.Instant

import io.herdes.master.domain.container.Container
import io.herdes.shared.orient.Entity

case class Engine(id: String,
                  name: String,
                  typeName: EngineType.Value,
                  address: String,
                  user: String,
                  password: String,
                  containersId: List[String] = List.empty,
                  status: EngineStatus.Value = EngineStatus.uninitialized,
                  createdAt: Long = Instant.now().getEpochSecond,
                  updatedAt: Long = Instant.now().getEpochSecond,
                  resources: Option[EngineResources] = Option.empty) extends Entity[String] {

  def addContainer(container: Container): Engine = {
    copy(containersId = container.id :: containersId)
  }
}

