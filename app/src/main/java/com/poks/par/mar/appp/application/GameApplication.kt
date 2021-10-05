package com.poks.par.mar.appp.application

import android.app.Application
import com.poks.par.mar.appp.data.Repository
import com.poks.par.mar.appp.di.AppComponent
import com.poks.par.mar.appp.di.DaggerAppComponent
import com.poks.par.mar.appp.di.RepositoryModule
import javax.inject.Inject

class GameApplication : Application() {
  
  @Inject
  lateinit var repository: Repository
  lateinit var appComponent: AppComponent

  override fun onCreate() {
    super.onCreate()

    setupDI()
    inject()
  }

  private fun setupDI() {
    appComponent = DaggerAppComponent.builder()
      .repositoryModule(RepositoryModule(applicationContext))
      .build()
  }

  private fun inject(): Unit = appComponent.inject(this)

  companion object {
    private const val TAG: String = "GameApplication"
  }
}