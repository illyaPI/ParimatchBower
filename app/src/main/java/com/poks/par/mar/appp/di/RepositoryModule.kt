package com.poks.par.mar.appp.di

import android.content.Context
import com.google.gson.Gson
import com.poks.par.mar.appp.data.GameRepository
import com.poks.par.mar.appp.data.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(
  private val appContext: Context
) {

  @Provides
  fun provideAppContext(): Context = appContext

  @Provides
  fun provideGson(): Gson = Gson()

  @Provides
  @Singleton
  fun provideRepository(appContext: Context, gson: Gson): Repository = GameRepository(appContext, gson)
}