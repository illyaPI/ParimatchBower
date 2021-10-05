package com.poks.par.mar.appp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.text.isDigitsOnly
import com.poks.par.mar.appp.application.GameApplication
import com.poks.par.mar.appp.data.Repository
import com.poks.par.mar.appp.databinding.FragmentGameBinding
import com.poks.par.mar.appp.util.ViewBindingFragment
import javax.inject.Inject

class GameFragment : ViewBindingFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {

  @Inject
  lateinit var repository: Repository

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    inject()
    setupTextInput()
  }

  private fun setupTextInput() {
    bnd.editTextLayoutGuess.isEndIconVisible = false

    bnd.editTextGuess.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
      override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
      override fun afterTextChanged(text: Editable?) {
        bnd.editTextLayoutGuess.isEndIconVisible = !text.isNullOrBlank() && text.length == 4 && text.isDigitsOnly()
      }
    })

    bnd.editTextLayoutGuess.setEndIconOnClickListener {
      requireActivity().currentFocus?.let { view ->
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
      }
      bnd.editTextLayoutGuess.clearFocus()
      bnd.editTextLayoutGuess.isEnabled = false
      Log.d(TAG, "setupTextInput: end icon clicked with number=${bnd.editTextGuess.text!!}")
    }
  }

  private fun inject(): Unit = (requireActivity().application as GameApplication).appComponent.inject(this)

  companion object {
    private const val TAG: String = "GameFragment"
  }
}