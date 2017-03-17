package io.herdes.master.infrastructure.provisioning

import io.herdes.master.domain.engine.{EngineResources, EngineResourcesRetrievingException}

import scala.util.matching.Regex.MatchData

class AnsibleEngineResourceParser(value: String) {
  def parse: EngineResources = {
    val pattern = "memory-total='(.*)', memory-free='(.*)', cpu='(.*)', cpu-vcpus='(.*)'".r
    pattern.findFirstMatchIn(value).getOrElse(throw new EngineResourcesRetrievingException())
  }

  implicit def toEngine(m: MatchData): EngineResources = {
    val cpuName = m.group(3)
    val cpuNumber = m.group(4).toInt
    val memoryTotal = m.group(1).toInt
    val memoryFree = m.group(2).toInt
    EngineResources(cpuName, cpuNumber, memoryTotal, memoryFree)
  }
}

object AnsibleEngineResourceParser {
  implicit def toParser(value: String): AnsibleEngineResourceParser = {
    new AnsibleEngineResourceParser(value)
  }
}

