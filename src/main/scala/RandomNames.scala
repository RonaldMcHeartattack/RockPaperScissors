package com.github.game

import scala.util.Random

object RandomNames {

  val names = List(
    "Yolo Swaggins",
    "Adolf the Toast Jesus",
    "Thomas the Wank Engine",
    "Thomas the Dank Engine",
    "Hugh J. Nus",
    "Roasty My Toasty",
    "Steve Irwin's Left Nut",
    "Bill Bye",
    "Dildo Gaggins",
    "Pong Lenis",
    "Passive Menis",
    "Stephen King Kong",
    "Alcoholocaust",
    "GrannyAnusPizza",
    "Ronald McHeartattack"
  )

  def get(): String = {
    Random.shuffle(names).head
  }

  def get(reservedName: String): String = {
    val name = get()
    if (name == reservedName) {
      return get(reservedName)
    }
    return name
  }


}
