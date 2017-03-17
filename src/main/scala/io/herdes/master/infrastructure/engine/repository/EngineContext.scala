package io.herdes.master.infrastructure.engine.repository

import io.herdes.master.domain.engine.Engine
import io.herdes.master.infrastructure.config.DatabaseConfigManager
import io.herdes.shared.orient.DocumentContext.{TD, TE}
import io.herdes.shared.orient.{DocumentContext, OrientContext}

object EngineContext extends DocumentContext[Engine] {
  override implicit val te: TE[Engine] = EngineMapper.documentToEngine
  override implicit val td: TD[Engine] = EngineMapper.engineToDocument
  override implicit val entityName: String = "Engine"
  override implicit val orientContext: OrientContext = DatabaseConfigManager
}
