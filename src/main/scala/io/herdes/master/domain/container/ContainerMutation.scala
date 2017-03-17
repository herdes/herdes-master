package io.herdes.master.domain.container

import java.time.Instant

case class ContainerMutation(id: String, name: String, updatedAt: Long = Instant.now().getEpochSecond)

object ContainerMutation {
  implicit def toContainerMutationOps(container: Container): ContainerMutationOps = {
    new ContainerMutationOps(container)
  }
}

class ContainerMutationOps(container: Container) {
  def mutate(m: ContainerMutation): Container = {
    container.copy(name = m.name, updatedAt = m.updatedAt)
  }
}