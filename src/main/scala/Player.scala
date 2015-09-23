package com.github.game

import scala.collection.mutable.MutableList
import scala.util.Random

class Player() {
  private var _name = ""
  private val history = MutableList[String]()

  def name(ln: String): Unit = { _name = ln }

  def name(): String = { _name }

  def sign(ln: String): String = {
    history += ln
    sign()
  }

  def randomSign(keys: Iterable[String]): String = {
    history += Random.shuffle(keys).head
    sign()
  }

  def sign(): String = {
    history.reverse.head
  }

  // def history(): List[String] {
  //   history.map(_)
  // }

}
