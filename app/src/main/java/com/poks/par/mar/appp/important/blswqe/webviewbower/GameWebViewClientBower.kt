package com.poks.par.mar.appp.important.blswqe.webviewbower

import android.content.Intent
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

open class GameWebViewClientBower(
  private val intentUrlsBower: List<String>,
  private val prohibitedUrlsBower: List<String>
) : WebViewClient() {

  override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
    val url = request.url.toString()
    return when {
      intentUrlsBower.find { url.startsWith(it) } != null -> {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, request.url))
        true
      }
      prohibitedUrlsBower.find { url.contains(it) } != null -> {
        Log.i(TAG_BOWER, "shouldOverrideUrlLoading: prohibited url occurred with url = $url")
        true
      }
      else -> false
    }
  }

  companion object {
    private const val TAG_BOWER: String = "GameWebViewClient"
  }
}