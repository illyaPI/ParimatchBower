package com.poks.par.mar.appp.data

import android.content.Context
import com.google.gson.Gson

class GameRepository(
  appContext: Context,
  private val gson: Gson
) : Repository {

  private val prefs = appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
  
  companion object {
    private const val PREFS: String = "PREFS"
  }
}