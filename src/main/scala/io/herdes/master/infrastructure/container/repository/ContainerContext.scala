package io.herdes.master.infrastructure.container.repository

import io.herdes.master.domain.container.Container
import io.herdes.master.infrastructure.config.DatabaseConfigManager
import io.herdes.shared.orient.{DocumentContext, OrientContext}
import io.herdes.shared.orient.DocumentContext.{TD, TE}

class ContainerContext extends DocumentContext[Container] {
  override implicit def te: TE[Container] = ContainerMapper.documentToEntity
  override implicit def td: TD[Container] = ContainerMapper.entityToDocument
  override implicit val entityName: String = "Container"
  override implicit val orientContext: OrientContext = DatabaseConfigManager
}
