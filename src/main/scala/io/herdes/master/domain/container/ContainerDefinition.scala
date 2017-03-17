package io.herdes.master.domain.container

import io.anydev.shared.UuidGenerator
import io.herdes.master.domain.engine.Engine

case class ContainerDefinition(name: String, engine: Engine, image: String, entryPoint: String)

object ContainerDefinition {
  implicit def toContainerDefinitionOps(containerDefinition: ContainerDefinition): ContainerDefinitionOps = {
    new ContainerDefinitionOps(containerDefinition)
  }
}

class ContainerDefinitionOps(definition: ContainerDefinition) {
  def container: Container = {
    val id = UuidGenerator.generate().toString
    Container(id, definition.name, definition.engine.id, definition.image, ContainerStatus.created)
  }
}