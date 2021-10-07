package com.poks.par.mar.appp.important.modelsbower

import com.poks.par.mar.appp.pampdebower.utilbower.initializationErrorBower

data class BinomLinkBower(
  val afStatusBower: AFStatusBower,

  var blackBower: String?,
  var keyBower: String?,

  val bundleBower: String,

  val sub2Bower: String?,
  val sub3Bower: String?,
  val sub4Bower: String?,
  val sub5Bower: String?,
  val sub6Bower: String?,
  var sub7Bower: String?,

  val sub10Bower: String
) {

  fun toLinkBower(): String {
    blackBower ?: initializationErrorBower("black")
    keyBower ?: initializationErrorBower("key")

    return when (afStatusBower) {
      AFStatusBower.ORGANIC_BOWER -> {
        sub7Bower ?: initializationErrorBower("sub7")
        "$blackBower?key=$keyBower&bundle=$bundleBower&sub7=$sub7Bower&sub10=$sub10Bower"
      }
      AFStatusBower.NON_ORGANIC_BOWER -> {
        val linkBuilderBower = StringBuilder("$blackBower?key=$keyBower&bundle=$bundleBower")
        linkBuilderBower.append(if (sub2Bower != null) "&sub2=$sub2Bower" else "")
        linkBuilderBower.append(if (sub3Bower != null) "&sub3=$sub3Bower" else "")
        linkBuilderBower.append(if (sub4Bower != null) "&sub4=$sub4Bower" else "")
        linkBuilderBower.append(if (sub5Bower != null) "&sub5=$sub5Bower" else "")
        linkBuilderBower.append(if (sub6Bower != null) "&sub6=$sub6Bower" else "")
        linkBuilderBower.append(if (sub7Bower != null) "&sub7=$sub7Bower" else "")
        linkBuilderBower.append("&sub10=$sub10Bower")
        linkBuilderBower.toString()
      }
    }
  }
}
