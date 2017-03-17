package io.herdes.master.interfaces

abstract sealed class RouteStructures
case class EngineMutationDTO(id: Option[String], name: String, typeName: String, address: String, user: String, password: String) extends RouteStructures
case class EngineDTO(id: String, name: String, typeName: String, address: String, user: String, password: String, containersId: List[String], status: String, createdAt: Long, updatedAt: Long) extends RouteStructures
