package com.poks.par.mar.appp.uibower.fragmentsbower

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.databinding.FragmentGameBowerBinding
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import com.poks.par.mar.appp.applicationbower.databower.gamebower.BullsAndCowsGameBower
import com.poks.par.mar.appp.important.modelsbower.TurnBower
import com.poks.par.mar.appp.uibower.activitiesbower.MainActivityBower
import com.poks.par.mar.appp.applicationbower.someact.adaptersbower.TurnAdapterBower
import com.poks.par.mar.appp.pampdebower.utilbower.ViewBindingFragmentBower
import com.poks.par.mar.appp.pampdebower.utilbower.hideKeyboardBower
import com.poks.par.mar.appp.pampdebower.utilbower.isCharactersUniqueBower
import kotlinx.coroutines.delay
import javax.inject.Inject

class GameFragmentBower : ViewBindingFragmentBower<FragmentGameBowerBinding>(FragmentGameBowerBinding::inflate) {

  @Inject
  lateinit var repositoryBower: RepositoryBower
  private lateinit var turnAdapterBower: TurnAdapterBower
  private val sizeBower = 4

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    injectBower()
    bndBower.imageViewBackBower.setOnClickListener { requireActivity().onBackPressed() }
    setupAdaptersBower()
    setupGameBower()
  }

  private fun setupGameBower() {
    val gameBower = BullsAndCowsGameBower(sizeBower)

    bndBower.textViewTryToGuessBower.text = getString(R.string.try_to_guess)

    validateGuessBower(bndBower.editTextGuessBower.text)
    bndBower.editTextGuessBower.addTextChangedListener(afterTextChanged = ::validateGuessBower)

    bndBower.editTextLayoutGuessBower.setEndIconOnClickListener {
      requireActivity().hideKeyboardBower()
      bndBower.editTextLayoutGuessBower.clearFocus()
      bndBower.editTextLayoutGuessBower.isEnabled = false

      val guessBower = bndBower.editTextGuessBower.text!!.toString().toInt()
      bndBower.editTextGuessBower.text?.clear()

      lifecycleScope.launchWhenCreated {
        when (val turnResultBower = gameBower.makeTurnBower(guessBower)) {
          is BullsAndCowsGameBower.ResultBower.TurnBower -> {
            val turnBower = TurnBower(turnResultBower.turnNumberBower, guessBower, turnResultBower.bullsBower, turnResultBower.cowsBower)
            turnAdapterBower.submitList(turnAdapterBower.currentList + turnBower) {
              bndBower.recyclerViewTurnBower.scrollToPosition(turnAdapterBower.itemCount - 1)
            }
            delay(TURN_DELAY_BOWER)
            bndBower.editTextLayoutGuessBower.isEnabled = true
          }
          is BullsAndCowsGameBower.ResultBower.EndBower -> {
            Toast.makeText(requireContext(), getString(R.string.you_win, turnResultBower.rightGuessBower), Toast.LENGTH_LONG).show()
            delay(TURN_DELAY_BOWER)
            bndBower.editTextLayoutGuessBower.isEnabled = true
            turnAdapterBower.submitList(null) {
              setupGameBower()
            }
          }
        }
      }
    }
  }

  private fun validateGuessBower(text: Editable?) {
    val isGuessValidBower = !text.isNullOrBlank() && text.length == sizeBower && text.isDigitsOnly() && text[0] != '0' && text.toString()
      .isCharactersUniqueBower()
    bndBower.editTextLayoutGuessBower.error = if (isGuessValidBower) null else getString(R.string.edit_text_guess_error_msg, sizeBower)
    bndBower.editTextLayoutGuessBower.isEndIconVisible = isGuessValidBower
  }

  private fun setupAdaptersBower() {
    val layoutManagerBower = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false).apply {
      stackFromEnd = true
    }
    bndBower.recyclerViewTurnBower.layoutManager = layoutManagerBower

    turnAdapterBower = TurnAdapterBower().apply {
      setHasStableIds(true)
    }
    bndBower.recyclerViewTurnBower.adapter = turnAdapterBower
  }

  override fun onResume() {
    super.onResume()
    if (repositoryBower.isMusicOnBower) (requireActivity() as MainActivityBower).mediaPlayerBower.start()
  }

  private fun injectBower(): Unit = (requireActivity().application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "GameFragment"
    private const val TURN_DELAY_BOWER: Long = 1000L
  }
}