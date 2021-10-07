package com.poks.par.mar.appp.applicationbower

import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import com.poks.par.mar.appp.BuildConfig
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import com.poks.par.mar.appp.dibower.AppComponentBower
import com.poks.par.mar.appp.dibower.DaggerAppComponentBower
import com.poks.par.mar.appp.dibower.RepositoryModuleBower
import com.poks.par.mar.appp.important.modelsbower.AFStatusBower
import com.poks.par.mar.appp.important.modelsbower.BinomLinkBower
import com.poks.par.mar.appp.pampdebower.utilbower.decodeFromBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameApplicationBower : Application() {

  @Inject
  lateinit var repositoryBower: RepositoryBower
  lateinit var appComponentBower: AppComponentBower
  private lateinit var googleAdvertisingIDBower: String
  private lateinit var appsFlyerIDBower: String

  override fun onCreate() {
    super.onCreate()

    setupDIBower()
    injectBower()
    setupGoogleAdvertisingIDBower()
    setupOneSignalBower()
    setupAppsFlyerBower()
  }

  private fun setupGoogleAdvertisingIDBower() {
    MobileAds.initialize(applicationContext)
    GlobalScope.launch(Dispatchers.IO) {
      googleAdvertisingIDBower = AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id
      Log.i(TAG_BOWER, "setupGoogleAdvertisingID: googleAdvertisingID: $googleAdvertisingIDBower")
    }
  }

  private fun setupOneSignalBower() {
    OneSignal.initWithContext(applicationContext)
    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
    OneSignal.setAppId(BuildConfig.ONE_SIGNAL_KEY_BOWER.decodeFromBase64())
  }

  private fun setupAppsFlyerBower() {
    val appsFlyerConversionListenerBower = object : AppsFlyerConversionListener {
      override fun onConversionDataFail(p0: String?) {}
      override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}
      override fun onAttributionFailure(p0: String?) {}

      override fun onConversionDataSuccess(dataMapBower: MutableMap<String, Any>?) {
        val sub10Bower = "$appsFlyerIDBower||$googleAdvertisingIDBower||${BuildConfig.APPS_FLYER_KEY_BOWER.decodeFromBase64()}"
        val binomLinkBower = when (val afStatus = dataMapBower?.get(AF_STATUS_BOWER)) {
          ORGANIC_BOWER -> {
            BinomLinkBower(
              afStatusBower = AFStatusBower.ORGANIC_BOWER, blackBower = null, keyBower = null,
              bundleBower = BuildConfig.APPLICATION_ID,
              sub2Bower = null, sub3Bower = null, sub4Bower = null, sub5Bower = null, sub6Bower = null,
              sub7Bower = ORGANIC_BOWER, sub10Bower = sub10Bower
            )
          }
          NON_ORGANIC_BOWER -> {
            val campaignBower = dataMapBower[CAMPAIGN_BOWER]?.toString()?.split("||")?.map { it.substringAfter(':') }
            BinomLinkBower(
              afStatusBower = AFStatusBower.NON_ORGANIC_BOWER, blackBower = null, keyBower = campaignBower?.getOrNull(1),
              bundleBower = BuildConfig.APPLICATION_ID,
              sub2Bower = campaignBower?.getOrNull(2), sub3Bower = campaignBower?.getOrNull(3),
              sub4Bower = dataMapBower[AD_GROUP_BOWER]?.toString(), sub5Bower = dataMapBower[AD_SET_BOWER]?.toString(),
              sub6Bower = dataMapBower[AF_CHANNEL_BOWER]?.toString(), sub7Bower = dataMapBower[MEDIA_SOURCE_BOWER]?.toString(),
              sub10Bower = sub10Bower
            )
          }
          else -> {
            Log.e(TAG_BOWER, "onConversionDataSuccess: unexpected value of afStatus == $afStatus")
            return
          }
        }
        repositoryBower.binomLinkBower = binomLinkBower

        dataMapBower.forEach { Log.i(TAG_BOWER, "onConversionDataSuccess: attribute: ${it.key} = ${it.value}") }
        Log.i(TAG_BOWER, "onConversionDataSuccess: binomLink: $binomLinkBower")
      }
    }
    AppsFlyerLib.getInstance().apply {
      init(BuildConfig.APPS_FLYER_KEY_BOWER.decodeFromBase64(), appsFlyerConversionListenerBower, applicationContext)
      start(applicationContext)
      appsFlyerIDBower = getAppsFlyerUID(applicationContext)
    }
    Log.i(TAG_BOWER, "setupAppsFlyer: appsFlyerId: $appsFlyerIDBower")
  }

  private fun setupDIBower() {
    appComponentBower = DaggerAppComponentBower.builder()
      .repositoryModuleBower(RepositoryModuleBower(applicationContext))
      .build()
  }

  private fun injectBower(): Unit = appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "GameApplication"

    //Keys for appsFlyerConversionListener
    private const val AF_STATUS_BOWER: String = "af_status"
    private const val ORGANIC_BOWER: String = "Organic"
    private const val NON_ORGANIC_BOWER: String = "Non-organic"
    private const val CAMPAIGN_BOWER: String = "campaign"
    private const val AD_GROUP_BOWER: String = "adgroup"
    private const val AD_SET_BOWER: String = "adset"
    private const val AF_CHANNEL_BOWER: String = "af_channel"
    private const val MEDIA_SOURCE_BOWER: String = "media_source"
  }
}