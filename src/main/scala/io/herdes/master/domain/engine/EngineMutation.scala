package io.herdes.master.domain.engine

import java.time.Instant

import io.herdes.master.domain.container.{Container, ContainerStatus}
import io.scalaberries.other.UuidGenerator

case class EngineMutation(id: Option[String], name: String, typeName: EngineType.Value, address: String, user: String, password: String, updatedAt: Long = Instant.now().getEpochSecond)

object EngineMutation {
  implicit def idToOptional(id: String): Option[String] = {
    Option.apply(id)
  }

  implicit def engineToEngineMutationOps(engine: Engine): EngineMutationOps = {
    new EngineMutationOps(engine)
  }

  implicit def mutationToEngine(m: EngineMutation): Engine = {
    val id = UuidGenerator.generate().toString
    val engine = Engine(id, m.name, m.typeName, m.address, m.user, m.password)
    engine
      .addContainer(Container("id", "name", id, "image", ContainerStatus.created))
      .addContainer(Container("id2", "name2", id, "image2", ContainerStatus.created))
  }
}

class EngineMutationOps(engine: Engine) {
  def mutate(m: EngineMutation): Engine = {
    engine.copy(name = m.name, typeName = m.typeName, address = m.address, user = m.user, password = m.password, updatedAt = m.updatedAt)
  }
}
