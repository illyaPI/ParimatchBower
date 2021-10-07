package com.poks.par.mar.appp.applicationbower.databower.gamebower

import com.poks.par.mar.appp.pampdebower.utilbower.isCharactersUniqueBower
import kotlin.random.Random

class BullsAndCowsGameBower(
  private val sizeBower: Int
) {

  private val numberBower: Int
  private var turnNumberBower = 1

  init {
    if (sizeBower !in 4..10) throw IllegalArgumentException("size=$sizeBower cannot be less than 4 or greater than 10")

    val randBower = Random(System.currentTimeMillis())
    var digitBower = randBower.nextInt(1000, 9999)
    while (!digitBower.toString().isCharactersUniqueBower()) digitBower = randBower.nextInt(1000, 9999)
    numberBower = digitBower
  }

  fun makeTurnBower(guessBower: Int): ResultBower {
    if (guessBower.toString().length != sizeBower) throw IllegalArgumentException("guess=$guessBower must be equal to size=$sizeBower")

    if (numberBower == guessBower) return ResultBower.EndBower(numberBower, turnNumberBower++)

    val guessStrBower = guessBower.toString()
    val numberStrBower = numberBower.toString()
    var cowsBower = 0
    var bullsBower = 0
    repeat(sizeBower) { i ->
      if (numberStrBower.contains(guessStrBower[i])) cowsBower++
      if (guessStrBower[i] == numberStrBower[i]) {
        bullsBower++
        cowsBower--
      }
    }
    return ResultBower.TurnBower(bullsBower, cowsBower, turnNumberBower++)
  }

  sealed class ResultBower {
    class TurnBower(val bullsBower: Int, val cowsBower: Int, val turnNumberBower: Int) : ResultBower()
    class EndBower(val rightGuessBower: Int, val turnNumberBower: Int) : ResultBower()
  }

  companion object {
    private const val TAG_BOWER: String = "BullsAndCowsGame"
  }
}