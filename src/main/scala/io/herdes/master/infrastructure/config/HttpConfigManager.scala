package io.herdes.master.infrastructure.config

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object HttpConfigManager {
  private val HttpPath = "akka.http"
  private val HttpHostPath = "host"
  private val HttpPortPath = "port"

  private val fallBackProperties = Map(
    HttpHostPath -> "localhost",
    HttpPortPath -> 8080).asJava

  private val fallBackConfig = ConfigFactory.parseMap(fallBackProperties)
  private val config = ConfigFactory.load().getConfig(HttpPath).withFallback(fallBackConfig)

  lazy val httpHost: String = config.getString(HttpHostPath)
  lazy val httpPort: Integer = config.getInt(HttpPortPath)
}
