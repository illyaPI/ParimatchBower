package com.poks.par.mar.appp.game

import com.poks.par.mar.appp.util.isCharactersUnique
import kotlin.random.Random

class BullsAndCowsGame(
  private val size: Int
) {

  private val number: Int
  private var turnNumber = 1

  init {
    if (size !in 4..10) throw IllegalArgumentException("size=$size cannot be less than 4 or greater than 10")

    val rand = Random(System.currentTimeMillis())
    var digit = rand.nextInt(1000, 9999)
    while (!digit.toString().isCharactersUnique()) digit = rand.nextInt(1000, 9999)
    number = digit
  }

  fun makeTurn(guess: Int): Result {
    if (guess.toString().length != size) throw IllegalArgumentException("guess=$guess must be equal to size=$size")

    if (number == guess) return Result.End(number, turnNumber++)

    val guessStr = guess.toString()
    val numberStr = number.toString()
    var cows = 0
    var bulls = 0
    repeat(size) { i ->
      if (numberStr.contains(guessStr[i])) cows++
      if (guessStr[i] == numberStr[i]) {
        bulls++
        cows--
      }
    }
    return Result.Turn(bulls, cows, turnNumber++)
  }

  sealed class Result {
    class Turn(val bulls: Int, val cows: Int, val turnNumber: Int) : Result()
    class End(val rightGuess: Int, val turnNumber: Int) : Result()
  }

  companion object {
    private const val TAG: String = "BullsAndCowsGame"
  }
}