package com.poks.par.mar.appp.uibower.fragmentsbower

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import com.poks.par.mar.appp.BuildConfig
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.databinding.FragmentWebViewBowerBinding
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import com.poks.par.mar.appp.important.servicesbower.InternetCheckerServiceBower
import com.poks.par.mar.appp.uibower.activitiesbower.MainActivityBower
import com.poks.par.mar.appp.important.blswqe.webviewbower.GameWebViewClientBower
import com.poks.par.mar.appp.pampdebower.utilbower.ViewBindingFragmentBower
import com.poks.par.mar.appp.pampdebower.utilbower.decodeFromBase64
import javax.inject.Inject

class PrivacyPolicyFragmentBower : ViewBindingFragmentBower<FragmentWebViewBowerBinding>(FragmentWebViewBowerBinding::inflate) {

  @Inject
  lateinit var repositoryBower: RepositoryBower
  private lateinit var intentFilterBower: IntentFilter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    injectBower()
    setupWebViewBower()
    setupServiceBower()
  }

  private fun setupWebViewBower() {
    val cookiesManNurKoBower: CookieManager = CookieManager.getInstance()
    CookieManager.setAcceptFileSchemeCookies(true)
    cookiesManNurKoBower.setAcceptThirdPartyCookies(bndBower.webViewBower, true)

    bndBower.webViewBower.webViewClient = GameWebViewClientBower(
      resources.getStringArray(R.array.intent_urls).toList(),
      resources.getStringArray(R.array.prohibited_urls).toList()
    )

    bndBower.webViewBower.loadUrl(BuildConfig.PRIVACY_POLICY_KEY_BOWER.decodeFromBase64())

    bndBower.layoutWebViewBower.setOnRefreshListener {
      bndBower.webViewBower.loadUrl(BuildConfig.PRIVACY_POLICY_KEY_BOWER.decodeFromBase64())
      bndBower.layoutWebViewBower.isRefreshing = false
    }
  }

  private fun setupServiceBower() {
    intentFilterBower = IntentFilter().apply {
      addAction(InternetCheckerServiceBower.INTERNET_CHECK_BOWER)
    }
    requireContext().startService(Intent(requireContext(), InternetCheckerServiceBower::class.java))
  }

  private val broadcastReceiverBower: BroadcastReceiver = object : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
      if (intent.action == InternetCheckerServiceBower.INTERNET_CHECK_BOWER) {
        if (intent.getBooleanExtra(InternetCheckerServiceBower.INTERNET_CHECK_BOWER, true)) {
          bndBower.layoutWebViewBower.visibility = View.VISIBLE
          bndBower.layoutErrorBower.visibility = View.GONE
        } else {
          bndBower.layoutWebViewBower.visibility = View.GONE
          bndBower.layoutErrorBower.visibility = View.VISIBLE
        }
      }
    }
  }

  override fun onPause() {
    super.onPause()
    requireContext().unregisterReceiver(broadcastReceiverBower)
  }

  override fun onResume() {
    super.onResume()
    if (repositoryBower.isMusicOnBower) (requireActivity() as MainActivityBower).mediaPlayerBower.start()
    requireContext().registerReceiver(broadcastReceiverBower, intentFilterBower)
  }

  private fun injectBower(): Unit = (requireActivity().application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "PrivacyPolicyFragment"
  }
}