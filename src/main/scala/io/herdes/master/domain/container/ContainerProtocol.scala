package io.herdes.master.domain.container

abstract sealed class ContainerProtocol
case class ListContainers() extends ContainerProtocol
case class CreateContainer(definition: ContainerDefinition) extends ContainerProtocol
case class SaveContainerMutation(mutation: ContainerMutation) extends ContainerProtocol
case class InspectContainer(id: String) extends ContainerProtocol
case class StartContainer(id: String) extends ContainerProtocol
case class StopContainer(id: String) extends ContainerProtocol
case class RestartContainer(id: String) extends ContainerProtocol
case class KillContainer(id: String) extends ContainerProtocol
case class DeleteContainer(id: String) extends ContainerProtocol
