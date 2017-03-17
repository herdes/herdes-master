package io.herdes.master.infrastructure.config

import com.typesafe.config.ConfigFactory
import io.herdes.shared.orient.OrientContext

import scala.collection.JavaConverters._

object DatabaseConfigManager extends OrientContext {
  private val DatabasePath = "akka.database"
  private val DatabaseProtocolPath = "protocol"
  private val DatabaseHostPath = "host"
  private val DatabaseNamePath = "name"
  private val DatabaseLoginPath = "login"
  private val DatabasePasswordPath = "password"
  private val EntityPackageName = "entity.package.name"

  private val fallBackProperties = Map(
    DatabaseProtocolPath -> "remote",
    DatabaseHostPath -> "localhost",
    DatabaseNamePath -> "unknown",
    DatabaseLoginPath -> "admin",
    DatabasePasswordPath -> "unknown").asJava

  private val fallBackConfig = ConfigFactory.parseMap(fallBackProperties)
  private val config = ConfigFactory.load().getConfig(DatabasePath).withFallback(fallBackConfig)

  lazy val databaseProtocol: String = config.getString(DatabaseProtocolPath)
  lazy val databaseHost: String = config.getString(DatabaseHostPath)
  lazy val databaseName: String = config.getString(DatabaseNamePath)
  lazy val databaseLogin: String = config.getString(DatabaseLoginPath)
  lazy val databasePassword: String = config.getString(DatabasePasswordPath)
  lazy val entityPackageName: String = config.getString(EntityPackageName)
}
