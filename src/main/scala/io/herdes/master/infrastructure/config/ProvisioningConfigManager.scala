package io.herdes.master.infrastructure.config

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

object ProvisioningConfigManager {
  private val ProvisioningPath = "akka.provisioning"
  private val LocationPath = "location"

  private val fallBackProperties = Map(
    LocationPath -> "/tmp").asJava

  private val fallBackConfig = ConfigFactory.parseMap(fallBackProperties)
  private val config = ConfigFactory.load().getConfig(ProvisioningPath).withFallback(fallBackConfig)

  lazy val location: String = config.getString(LocationPath)
}
