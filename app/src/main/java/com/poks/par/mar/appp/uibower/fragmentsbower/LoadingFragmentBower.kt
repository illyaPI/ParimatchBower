package com.poks.par.mar.appp.uibower.fragmentsbower

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoadingFragmentBower : Fragment(R.layout.fragment_loading_bower) {

  @Inject
  lateinit var repositoryBower: RepositoryBower

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    injectBower()
    if (repositoryBower.lastBinomLinkBower != null) {
      findNavController().navigate(R.id.action_loadingFragment_to_webViewFragment)
    }
    lifecycleScope.launchWhenResumed {
      delay(START_DELAY_BOWER)
      setupBlackAndKeyBower()
    }
  }

  private fun setupBlackAndKeyBower() {
    val firebaseConfigBower = FirebaseRemoteConfig.getInstance()
    firebaseConfigBower.fetchAndActivate().addOnCompleteListener(requireActivity()) {
      if (!it.isSuccessful) {
        findNavController().navigate(R.id.action_loadingFragment_to_menuFragment)
        return@addOnCompleteListener
      }

      val blackBower = firebaseConfigBower.getString(BLACK_KEY_BOWER)
      val keyBower = firebaseConfigBower.getString(KEY_KEY_BOWER)
      Log.i(TAG_BOWER, "setupBlackAndKey: black: $blackBower")
      Log.i(TAG_BOWER, "setupBlackAndKey: key: $keyBower")

      if (blackBower == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_STRING || keyBower == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_STRING) {
        findNavController().navigate(R.id.action_loadingFragment_to_menuFragment)
      } else {
        repositoryBower.blackBower = blackBower
        repositoryBower.keyBower = keyBower
        findNavController().navigate(R.id.action_loadingFragment_to_webViewFragment)
      }
    }
  }

  private fun injectBower(): Unit = (requireActivity().application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "LoadingFragment"
    private const val START_DELAY_BOWER: Long = 6000L
    private const val BLACK_KEY_BOWER: String = "poksblackpage"
    private const val KEY_KEY_BOWER: String = "poksdefaultkey"
  }
}