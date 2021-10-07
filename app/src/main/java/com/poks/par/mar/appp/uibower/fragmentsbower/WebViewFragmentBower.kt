package com.poks.par.mar.appp.uibower.fragmentsbower

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.addCallback
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.poks.par.mar.appp.BuildConfig
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.databinding.FragmentWebViewBowerBinding
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import com.poks.par.mar.appp.important.modelsbower.AFStatusBower
import com.poks.par.mar.appp.important.servicesbower.InternetCheckerServiceBower
import com.poks.par.mar.appp.important.blswqe.webviewbower.GameWebViewClientBower
import com.poks.par.mar.appp.pampdebower.utilbower.ViewBindingFragmentBower
import com.poks.par.mar.appp.pampdebower.utilbower.checkForPermissionsBower
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WebViewFragmentBower : ViewBindingFragmentBower<FragmentWebViewBowerBinding>(FragmentWebViewBowerBinding::inflate) {

  @Inject
  lateinit var repositoryBower: RepositoryBower
  private lateinit var binomLinkStringBower: String

  private lateinit var intentFilterBower: IntentFilter
  private var filePathCallbackBower: ValueCallback<Array<Uri>>? = null
  private var uriViewBower: Uri = Uri.EMPTY

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    injectBower()
    requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    setupBinomLinkBower()
    setupWebViewBower()
    setupOnBackPressedBower()
    setupServiceBower()
  }

  private fun setupBinomLinkBower() {
    val binomLinkBower = repositoryBower.binomLinkBower
    val blackBower = repositoryBower.blackBower
    val keyBower = repositoryBower.keyBower
    if (binomLinkBower != null) {
      when (binomLinkBower.afStatusBower) {
        AFStatusBower.ORGANIC_BOWER -> {
          binomLinkBower.blackBower = blackBower
          binomLinkBower.keyBower = keyBower
        }
        AFStatusBower.NON_ORGANIC_BOWER -> {
          binomLinkBower.blackBower = blackBower
          val nonOrganicKeyBower = binomLinkBower.keyBower
          if (nonOrganicKeyBower == null || nonOrganicKeyBower.length != 20) {
            binomLinkBower.keyBower = keyBower
            binomLinkBower.sub7Bower = "Default"
          }
        }
      }
      repositoryBower.binomLinkBower = binomLinkBower
      binomLinkStringBower = binomLinkBower.toLinkBower()
    } else {
      binomLinkStringBower = "$blackBower?key=$keyBower"
    }
    Log.i(TAG_BOWER, "setupBinomLink: binomLink: $binomLinkBower")
    Log.i(TAG_BOWER, "setupBinomLink: binomLinkString: $binomLinkStringBower")
  }

  private fun setupWebViewBower() {
    val cookiesManBower: CookieManager = CookieManager.getInstance()
    CookieManager.setAcceptFileSchemeCookies(true)
    cookiesManBower.setAcceptThirdPartyCookies(bndBower.webViewBower, true)

    bndBower.webViewBower.webViewClient = object : GameWebViewClientBower(
      resources.getStringArray(R.array.intent_urls).toList(),
      resources.getStringArray(R.array.prohibited_urls).toList()
    ) {

      override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
        if (url.contains(BuildConfig.APPLICATION_ID.replace('.', '_'))) {
          findNavController().navigate(R.id.action_webViewFragment_to_menuFragment)
        } else {
          super.onPageStarted(view, url, favicon)
        }
      }
    }

    bndBower.webViewBower.webChromeClient = object : WebChromeClient() {

      override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
      ): Boolean {
        requireContext().checkForPermissionsBower()
        this@WebViewFragmentBower.filePathCallbackBower = filePathCallback
        val capIntentBower = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (capIntentBower.resolveActivity(requireActivity().packageManager) != null) {
          val providedFileBower = createTempFileBower()
          uriViewBower = FileProvider.getUriForFile(
            requireContext(),
            "${requireActivity().application.packageName}.provider",
            providedFileBower
          )
          capIntentBower.apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uriViewBower)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
          }
          val actionIntentBower = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
          }
          val intentChooserBower = Intent(Intent.ACTION_CHOOSER).apply {
            putExtra(Intent.EXTRA_INTENT, capIntentBower)
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(actionIntentBower))
          }
          startActivityForResult(intentChooserBower, RESULT_BOWER)
          return true
        }
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
      }
    }

    bndBower.webViewBower.loadUrl(repositoryBower.lastBinomLinkBower ?: binomLinkStringBower)

    bndBower.layoutWebViewBower.setOnRefreshListener {
      bndBower.webViewBower.loadUrl(binomLinkStringBower)
      bndBower.layoutWebViewBower.isRefreshing = false
    }
  }

  private fun setupOnBackPressedBower() {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
      if (bndBower.webViewBower.canGoBack()) bndBower.webViewBower.goBack()
      else requireActivity().finish()
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

  override fun onStop() {
    super.onStop()
    repositoryBower.lastBinomLinkBower = bndBower.webViewBower.url
  }

  override fun onResume() {
    super.onResume()
    requireContext().registerReceiver(broadcastReceiverBower, intentFilterBower)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == RESULT_BOWER) {
      if (filePathCallbackBower == null) return
      val uriResultBower = if (data == null || resultCode != Activity.RESULT_OK) null else data.data
      if (uriResultBower != null && filePathCallbackBower != null) {
        filePathCallbackBower!!.onReceiveValue(arrayOf(uriResultBower))
      } else if (filePathCallbackBower != null) {
        filePathCallbackBower!!.onReceiveValue(arrayOf(uriViewBower))
      }
    }
    filePathCallbackBower = null
    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun createTempFileBower(): File {
    val dateBower = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileDirBower = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("TMP${dateBower}_BOWER", ".jpg", fileDirBower)
  }

  private fun injectBower(): Unit = (requireActivity().application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "WebViewFragment"
    private const val RESULT_BOWER: Int = 0
  }
}