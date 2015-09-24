package com.github.game

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter

class PlayerTests extends FunSuite with BeforeAndAfter {

  var player: Player = _

  before {
    player = new Player()
  }

  test("should set and get name") {
    val name = "test user"
    player.name(name)
    assert(player.name == name)
  }

  test("should set and get sign") {
    val sign = "rock"
    player.sign(sign)
    assert(player.sign == sign)
  }

  test("should set randon sign") {
    val signs = Set("rock", "paper", "scissors")
    assert(signs.contains(player.randomSign(signs)))
  }

}
