package com.github.game

import org.scalatest._

class GameTests extends FunSuite with BeforeAndAfter with Matchers {

  var game: Game = _

  before {
    game = new Game()
  }

  test("should process all steps in single mode") {
    assert(game.process("start") == game.messages("chooseType"))
    assert(game.process("single") == game.messages("chooseName"))
    val msg = game.process("Maxim")
    msg should include ("Maxim")
    msg should include (game.messages("chooseSign"))
    game.process("rock") should include regex "lose|win|Same gesture"
  }

  test("should process all steps in observer mode") {
    assert(game.process("start") == game.messages("chooseType"))
    game.process("observer") should include ("stage")
    val msg = game.process("")
    msg should include regex "Win|Same gesture"
    msg should include regex "Lets play?"
  }

}
