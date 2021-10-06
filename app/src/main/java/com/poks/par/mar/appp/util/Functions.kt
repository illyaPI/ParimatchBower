package com.poks.par.mar.appp.util

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.view.inputmethod.InputMethodManager

fun String.decodeFromBase64(): String = String(Base64.decode(this, Base64.DEFAULT))

fun initializationError(propertyName: String): Nothing = throw IllegalStateException("$propertyName has not been initialized yet")

fun String.isCharactersUnique(): Boolean = toSet().size == length

fun Activity.hideKeyboard() {
  currentFocus?.let { view ->
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
  }
}