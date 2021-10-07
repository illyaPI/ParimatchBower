package com.poks.par.mar.appp.important.blswqe.webviewbower

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView

class GameWebViewBower(contextBower: Context, attrsBower: AttributeSet) : WebView(contextBower, attrsBower) {

  init {
    scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

    settings.apply {
      javaScriptEnabled = true
      useWideViewPort = true
      loadWithOverviewMode = true
      mediaPlaybackRequiresUserGesture = false
      domStorageEnabled = true
      displayZoomControls = false
      defaultTextEncodingName = "utf-8"
      builtInZoomControls = true
      cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    }
  }
}