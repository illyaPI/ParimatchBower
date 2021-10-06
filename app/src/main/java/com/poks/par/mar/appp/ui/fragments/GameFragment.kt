package com.poks.par.mar.appp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.application.GameApplication
import com.poks.par.mar.appp.data.Repository
import com.poks.par.mar.appp.databinding.FragmentGameBinding
import com.poks.par.mar.appp.game.BullsAndCowsGame
import com.poks.par.mar.appp.models.Turn
import com.poks.par.mar.appp.ui.adapters.TurnAdapter
import com.poks.par.mar.appp.util.ViewBindingFragment
import com.poks.par.mar.appp.util.hideKeyboard
import com.poks.par.mar.appp.util.isCharactersUnique
import kotlinx.coroutines.delay
import javax.inject.Inject

class GameFragment : ViewBindingFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {

  @Inject
  lateinit var repository: Repository
  private lateinit var turnAdapter: TurnAdapter
  private var turnCounter = 1
  private val size = 4

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    inject()
    setupAdapters()
    setupGame()
  }

  private fun setupGame() {
    val game = BullsAndCowsGame(size)

    bnd.textViewTryToGuess.text = getString(R.string.try_to_guess, size)

    validateGuess(bnd.editTextGuess.text)
    bnd.editTextGuess.addTextChangedListener(afterTextChanged = ::validateGuess)

    bnd.editTextLayoutGuess.setEndIconOnClickListener {
      requireActivity().hideKeyboard()
      bnd.editTextLayoutGuess.clearFocus()
      bnd.editTextLayoutGuess.isEnabled = false

      val guess = bnd.editTextGuess.text!!.toString().toInt()
      bnd.editTextGuess.text?.clear()

      lifecycleScope.launchWhenCreated {
        when (val turnResult = game.makeTurn(guess)) {
          is BullsAndCowsGame.Result.Turn -> {
            val turn = Turn(turnResult.turnNumber, guess, turnResult.bulls, turnResult.cows)
            turnAdapter.submitList(turnAdapter.currentList + turn) {
              bnd.recyclerViewTurn.scrollToPosition(turnAdapter.itemCount - 1)
            }
            delay(TURN_DELAY)
            bnd.editTextLayoutGuess.isEnabled = true
          }
          is BullsAndCowsGame.Result.End -> {
            Toast.makeText(requireContext(), getString(R.string.you_win, turnResult.rightGuess), Toast.LENGTH_LONG).show()
            delay(TURN_DELAY)
            bnd.editTextLayoutGuess.isEnabled = true
            turnAdapter.submitList(null) {
              setupGame()
            }
          }
        }
      }
    }
  }

  private fun validateGuess(text: Editable?) {
    val isGuessValid = !text.isNullOrBlank() && text.length == size && text.isDigitsOnly() && text[0] != '0' && text.toString()
      .isCharactersUnique()
    bnd.editTextLayoutGuess.error = if (isGuessValid) null else getString(R.string.edit_text_guess_error_msg, size)
    bnd.editTextLayoutGuess.isEndIconVisible = isGuessValid
  }

  private fun setupAdapters() {
    val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false).apply {
      stackFromEnd = true
    }
    bnd.recyclerViewTurn.layoutManager = layoutManager

    turnAdapter = TurnAdapter().apply {
      setHasStableIds(true)
    }
    bnd.recyclerViewTurn.adapter = turnAdapter
  }

  private fun inject(): Unit = (requireActivity().application as GameApplication).appComponent.inject(this)

  companion object {
    private const val TAG: String = "GameFragment"
    private const val TURN_DELAY: Long = 1000L
  }
}