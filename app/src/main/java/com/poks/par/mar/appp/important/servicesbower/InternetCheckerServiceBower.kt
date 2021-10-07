package com.poks.par.mar.appp.important.servicesbower

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.SystemClock

class InternetCheckerServiceBower : Service() {

  private val internetHandlerBower: Handler = Handler()

  private val internetRunnableBower: Runnable = object : Runnable {

    override fun run() {
      internetHandlerBower.postDelayed(this, 1000 - SystemClock.elapsedRealtime() % 578)
      val internetIntentBower = Intent().apply {
        action = INTERNET_CHECK_BOWER
        putExtra(INTERNET_CHECK_BOWER, isInternetAvailableMares())
      }
      sendBroadcast(internetIntentBower)
    }
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    internetHandlerBower.post(internetRunnableBower);
    return START_STICKY;
  }

  private fun isInternetAvailableMares(): Boolean {
    val cmBower = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return when {
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
        val capBower = cmBower.getNetworkCapabilities(cmBower.activeNetwork) ?: return false
        return capBower.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      }
      else -> {
        for (nBower in cmBower.allNetworks) {
          val nInfoBower = cmBower.getNetworkInfo(nBower)
          if (nInfoBower != null && nInfoBower.isConnected) return true
        }
        false
      }
    }
  }

  override fun onBind(intent: Intent): IBinder? = null

  companion object {
    const val INTERNET_CHECK_BOWER: String = "INTERNET_CHECK"
  }
}