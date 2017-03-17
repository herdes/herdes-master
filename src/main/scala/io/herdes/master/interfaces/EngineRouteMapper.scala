package io.herdes.master.interfaces

import io.herdes.master.domain.engine.{Engine, EngineMutation, EngineType}

object EngineRouteMapper {

  private[interfaces] implicit def dtoToEntity(dto: EngineMutationDTO): EngineMutation = {
    EngineMutation(dto.id, dto.name, EngineType.withName(dto.typeName), dto.address, dto.user, dto.password)
  }

  private[interfaces] implicit def entityToDto(e: Engine): EngineDTO = {
    EngineDTO(e.id, e.name, e.typeName.toString, e.address, e.user, e.password, e.containersId, e.status.toString, e.createdAt, e.updatedAt)
  }

  private[interfaces] implicit def entityToDtoList(engines: List[Engine]): List[EngineDTO] = {
    engines.map(engine => entityToDto(engine))
  }
}
