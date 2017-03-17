package io.herdes.master

import io.anydev.shared.UuidGenerator

object TestUtils {

  def randomString: String = {
    UuidGenerator.generate().toString
  }
}
