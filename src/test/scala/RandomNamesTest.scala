package com.github.game

import org.scalatest.FunSuite

class RandomNamesTests extends FunSuite {

  test("should return random name") {
    assert(RandomNames.get.size != 0)
  }

  test("should return random name but not selected name") {
    val name = RandomNames.get
    assert(RandomNames.get(name) != name)
  }

}
