package io.herdes.master.infrastructure.provisioning

import io.herdes.master.domain.engine.Engine

abstract class ProvisionProtocol
case class Provision(engine: Engine)
case class GetResources(engine: Engine)
