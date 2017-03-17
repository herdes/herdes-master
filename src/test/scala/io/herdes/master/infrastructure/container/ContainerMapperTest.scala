package io.herdes.master.infrastructure.container

import io.herdes.master.TestUtils.randomString
import io.herdes.master.domain.container.{Container, ContainerStatus}
import io.herdes.master.domain.engine.Engine
import io.herdes.master.infrastructure.container.repository.ContainerMapper
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, GivenWhenThen, ShouldMatchers}

class ContainerMapperTest extends FlatSpec with ShouldMatchers with GivenWhenThen with MockitoSugar {

  private val engine = mock[Engine]

  "A mapping functions" should "be reflexive" in {
    Given("valid Container object")
    val container = Container(randomString, randomString, randomString, randomString, ContainerStatus.created)

    When("map to document and backwards")
    val document = ContainerMapper.entityToDocument(container)
    val retContainer = ContainerMapper.documentToEntity(document)

    Then("returned container is equal to given container")
    retContainer should equal(container)
  }

  "New document" should "have proper class name set" in {
    Given("valid Container object")
    val container = Container(randomString, randomString, randomString, randomString, ContainerStatus.created)

    When("map to document")
    val document = ContainerMapper.entityToDocument(container)

    Then("document has proper class name set")
    document should have('className ("Container"))
  }
}
