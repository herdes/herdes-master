package io.herdes.master.domain.engine

trait EngineRepository {
  def findById(id: String): Engine
  def findByIdOptional(id: String): Option[Engine]
  def findAll(): List[Engine]
  def save(engine: Engine): Engine
  def delete(id: Engine): Engine
}
