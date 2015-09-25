package com.github.game

case class IncorrectInputException(msg: String) extends Exception(msg)

class Game() {
  var state = ""
  var gameType = ""
  val firstPlayer = new Player
  val secondPlayer = new Player
  val messages = Map(
    "chooseType" -> """Please choose type of game
                       |`single` - you against computer
                       |`observer` - show how computer play against another computer""".stripMargin,
    "chooseName" -> "Choose your name or skip skip this step",
    "chooseSign" -> "Your turn, use `rock`, `paper` or `scissors` as options",
    "replayGame" -> "Play again? Or change game type?(yes/no/change)",
    "computerPlay" -> "Lets play?(just press enter)"
  )
  val stateActions = Map(
    "" -> { (ln: String) => Unit },
    "chooseType" -> { chooseType(_) },
    "chooseName" -> { chooseName(_) },
    "chooseSign" -> { chooseSign(_) },
    "replayGame" -> { replayGame(_) },
    "computerPlay" -> { computerPlay(_) }
  )
  val gestures = Map[String, Int](
    "rock" -> 0,
    "paper" -> 1,
    "scissors" -> 2
  )

  def process(ln: String): String = {
    try {
       stateActions(state)(ln) match {
        case s: String => "%s\n%s".format(s, next())
        case _ => next()
      }
    } catch {
        case IncorrectInputException(msg) => msg
    }
  }

  private def next(): String = {
    state = state match {
      case "" => "chooseType"
      case "chooseType" if gameType == "observer" => "computerPlay"
      case "chooseType" => "chooseName"
      case "chooseName" => "chooseSign"
      case "computerPlay" => "computerPlay"
      case "chooseSign" => "replayGame"
    }
    messages(state)
  }

  private def chooseType(ln: String): Any = ln match {
    case "single" => gameType = "single"
    case "observer" => observerGameType()
    case _ => throw IncorrectInputException("Wrong choise")
  }

  private def observerGameType(): String = {
    firstPlayer.name(RandomNames.get())
    secondPlayer.name(RandomNames.get(firstPlayer.name()))
    gameType = "observer"
    "On stage %s against %s".format(firstPlayer.name, secondPlayer.name)
  }

  private def chooseSign(ln: String): String = {
    secondPlayer.randomSign(gestures.keys)
    firstPlayer.sign(ln match {
      case s: String if gestures.contains(s) => s
      case _ => throw IncorrectInputException("Wrong sign")
    })
    chooseWiner(List[Player](firstPlayer, secondPlayer)) match {
      case 1 => "You win with `%s` against `%s`".format(firstPlayer.sign(),
        secondPlayer.sign())
      case _ => "You lose your `%s` against `%s`".format(firstPlayer.sign(),
        secondPlayer.sign())
    }
  }

  private def chooseWiner(players: List[Player]): Int = {
    val dividend = gestures(firstPlayer.sign()) - gestures(secondPlayer.sign())
    val divisor = 3
    val result = ((dividend % divisor) + divisor) % divisor
    result match {
      case 1 | 2 => result
      case 0 if gameType == "observer" => throw IncorrectInputException("Same gesture `%s`. Just press enter for replay".format(firstPlayer.sign()))
      case 0 => throw IncorrectInputException("Same gesture `%s`. Please, throw again `rock`, `paper` or `scissors`".format(
        firstPlayer.sign()
      ))
    }
  }

  private def setComputerNames() = {
    firstPlayer.name(RandomNames.get())
    secondPlayer.name(RandomNames.get(firstPlayer.name()))
    state = "computerPlay"
    "On stage %s against %s".format(firstPlayer.name, secondPlayer.name)
  }

  private def computerPlay(ln: String): String = {
    firstPlayer.randomSign(gestures.keys)
    secondPlayer.randomSign(gestures.keys)
    val msg = "Win \"%s\" with `%s` against `%s`"
    chooseWiner(List[Player](firstPlayer, secondPlayer)) match {
      case 1 => msg.format(firstPlayer.name, firstPlayer.sign(), secondPlayer.sign())
      case 2 => msg.format(secondPlayer.name, secondPlayer.sign(), firstPlayer.sign())
    }
  }

  private def chooseName(ln: String): String = {
    firstPlayer.name(ln match {
      case "" => RandomNames.get()
      case _ => ln
    })
    secondPlayer.name(RandomNames.get(firstPlayer.name()))
    "Ok, you are \"%s\" and play against \"%s\"".format(firstPlayer.name(),
      secondPlayer.name())
  }

  private def replayGame(ln: String): Unit = {
    state = (ln match {
      case "" | "yes" | "y" if gameType == "single" => "chooseName"
      case "" | "yes" | "y" if gameType == "observer" => "computerPlay"
      case "change" | "c" => ""
      case "no" | "not" | "exit" => sys.exit(0)
      case _ => throw IncorrectInputException("Wrong answer")
    })
  }

}
