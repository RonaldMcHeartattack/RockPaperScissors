package com.github.game

// add help rules
object Main extends App {
  sys addShutdownHook(shutdown)

  println("Let's start play Rock, Paper, Scissors!")
  val game = new Game()

  private def shutdown {
    println("there should be statistics about game")
  }

  println(game.process("start"))
  for (ln <- io.Source.stdin.getLines) println(game.process(ln))
 }
