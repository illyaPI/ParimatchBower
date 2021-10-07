package com.poks.par.mar.appp.dibower

import android.content.Context
import com.google.gson.Gson
import com.poks.par.mar.appp.applicationbower.databower.GameRepositoryBower
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModuleBower(
  private val appContextBower: Context
) {

  @Provides
  fun provideAppContextBower(): Context = appContextBower

  @Provides
  fun provideGsonBower(): Gson = Gson()

  @Provides
  @Singleton
  fun provideRepositoryBower(appContextBower: Context, gsonBower: Gson): RepositoryBower =
    GameRepositoryBower(appContextBower, gsonBower)
}