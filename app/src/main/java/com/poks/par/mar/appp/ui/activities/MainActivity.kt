package com.poks.par.mar.appp.ui.activities

import android.os.Bundle
import com.poks.par.mar.appp.application.GameApplication
import com.poks.par.mar.appp.data.Repository
import com.poks.par.mar.appp.databinding.ActivityMainBinding
import com.poks.par.mar.appp.util.ViewBindingActivity
import javax.inject.Inject

class MainActivity : ViewBindingActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

  @Inject
  lateinit var repository: Repository

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    inject()
  }

  private fun inject(): Unit = (application as GameApplication).appComponent.inject(this)

  companion object {
    private const val TAG: String = "MainActivity"
  }
}