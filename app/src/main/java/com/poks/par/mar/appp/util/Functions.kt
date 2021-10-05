package com.poks.par.mar.appp.util

import android.util.Base64

fun String.decodeFromBase64(): String = String(Base64.decode(this, Base64.DEFAULT))

fun initializationError(propertyName: String): Nothing = throw IllegalStateException("$propertyName has not been initialized yet")