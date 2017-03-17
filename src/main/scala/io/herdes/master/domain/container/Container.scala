package io.herdes.master.domain.container

import java.time.Instant

import io.herdes.shared.orient.Entity

case class Container(id: String, name: String, engineId: String, image: String, status: ContainerStatus.Value, createdAt: Long = Instant.now().getEpochSecond, updatedAt: Long = Instant.now().getEpochSecond) extends Entity[String]
