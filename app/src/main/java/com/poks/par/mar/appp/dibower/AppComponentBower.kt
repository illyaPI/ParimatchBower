package com.poks.par.mar.appp.dibower

import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.uibower.activitiesbower.MainActivityBower
import com.poks.par.mar.appp.uibower.fragmentsbower.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModuleBower::class])
interface AppComponentBower {

  fun inject(applicationBower: GameApplicationBower)
  fun inject(activityBower: MainActivityBower)
  fun inject(fragmentBower: LoadingFragmentBower)
  fun inject(fragmentBower: MenuFragmentBower)
  fun inject(fragmentBower: CreateProfileFragmentBower)
  fun inject(fragmentBower: ProfileFragmentBower)
  fun inject(fragmentBower: GameFragmentBower)
  fun inject(fragmentBower: PrivacyPolicyFragmentBower)
  fun inject(fragmentBower: WebViewFragmentBower)
}