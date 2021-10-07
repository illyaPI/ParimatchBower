package com.poks.par.mar.appp.applicationbower.databower

import android.content.Context
import com.google.gson.Gson
import com.poks.par.mar.appp.important.modelsbower.BinomLinkBower
import com.poks.par.mar.appp.important.modelsbower.UserBower
import com.poks.par.mar.appp.pampdebower.utilbower.initializationErrorBower

class GameRepositoryBower(
  appContextBower: Context,
  private val gsonBower: Gson
) : RepositoryBower {

  private val prefsBower = appContextBower.getSharedPreferences(PREFS_BOWER, Context.MODE_PRIVATE)

  override var binomLinkBower: BinomLinkBower?
    get() {
      val binomLinkStringBower = prefsBower.getString(BINOM_LINK_BOWER, null) ?: return null
      return gsonBower.fromJson(binomLinkStringBower, BinomLinkBower::class.java)
    }
    set(value) = prefsBower.edit().putString(BINOM_LINK_BOWER, gsonBower.toJson(value)).apply()

  override var blackBower: String
    get() = prefsBower.getString(BLACK_BOWER, null) ?: initializationErrorBower("black")
    set(value) = prefsBower.edit().putString(BLACK_BOWER, value).apply()

  override var keyBower: String
    get() = prefsBower.getString(KEY_BOWER, null) ?: initializationErrorBower("key")
    set(value) = prefsBower.edit().putString(KEY_BOWER, value).apply()

  override var userBowerBower: UserBower?
    get() {
      val userString = prefsBower.getString(USER_BOWER, null) ?: return null
      return gsonBower.fromJson(userString, UserBower::class.java)
    }
    set(value) = prefsBower.edit().putString(USER_BOWER, gsonBower.toJson(value)).apply()

  override var isMusicOnBower: Boolean
    get() = prefsBower.getBoolean(IS_MUSIC_ON_BOWER, true)
    set(value) = prefsBower.edit().putBoolean(IS_MUSIC_ON_BOWER, value).apply()

  override var lastBinomLinkBower: String?
    get() = prefsBower.getString(LAST_BINOM_LINK_BOWER, null)
    set(value) = prefsBower.edit().putString(LAST_BINOM_LINK_BOWER, value).apply()

  companion object {
    private const val PREFS_BOWER: String = "PREFS"

    private const val BINOM_LINK_BOWER = "BINOM_LINK"
    private const val BLACK_BOWER = "BLACK"
    private const val KEY_BOWER = "KEY"

    private const val USER_BOWER = "USER"
    private const val IS_MUSIC_ON_BOWER = "IS_MUSIC_ON"

    private const val LAST_BINOM_LINK_BOWER = "LAST_BINOM_LINK"
  }
}