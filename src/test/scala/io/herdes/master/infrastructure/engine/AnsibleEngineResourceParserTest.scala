package io.herdes.master.infrastructure.engine

import org.scalatest.{FlatSpec, GivenWhenThen, ShouldMatchers}
import io.herdes.master.infrastructure.provisioning.AnsibleEngineResourceParser._

class AnsibleEngineResourceParserTest extends FlatSpec with GivenWhenThen with ShouldMatchers {

  "A parser" should "retrieve engine resources" in {
    Given("a response line")
    val line =
      """
        |ok: [37.233.101.159] => {
        |    "msg": "memory-total='2002', memory-free='1893', cpu='Intel(R) Xeon(R) CPU           L5639  @ 2.13GHz', cpu-vcpus='2'"
        |}
        |
        |
      """.stripMargin

    When("parse is invoked on command line output")
    val machineResources = line.parse

    Then("parsed resources should be returned")
    machineResources.cpuName shouldBe "Intel(R) Xeon(R) CPU           L5639  @ 2.13GHz"
    machineResources.cpuNumber shouldBe 2
    machineResources.memoryTotal shouldBe 2002
    machineResources.memoryFree shouldBe 1893
  }
}
