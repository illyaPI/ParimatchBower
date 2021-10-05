package com.poks.par.mar.appp.di

import com.poks.par.mar.appp.application.GameApplication
import com.poks.par.mar.appp.ui.activities.MainActivity
import com.poks.par.mar.appp.ui.fragments.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {

  fun inject(application: GameApplication)
  fun inject(activity: MainActivity)
  fun inject(fragment: LoadingFragment)
  fun inject(fragment: MenuFragment)
  fun inject(fragment: CreateProfileFragment)
  fun inject(fragment: ProfileFragment)
  fun inject(fragment: GameFragment)
  fun inject(fragment: PrivacyPolicyFragment)
  fun inject(fragment: WebViewFragment)
}