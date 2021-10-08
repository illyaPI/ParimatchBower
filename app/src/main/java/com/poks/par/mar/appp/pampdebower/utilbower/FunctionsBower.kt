package com.poks.par.mar.appp.pampdebower.utilbower

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.view.inputmethod.InputMethodManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

fun String.decodeFromBase64Bower(): String = String(Base64.decode(this, Base64.DEFAULT))

fun initializationErrorBower(propertyNameBower: String): Nothing =
  throw IllegalStateException("$propertyNameBower has not been initialized yet")

fun String.isCharactersUniqueBower(): Boolean = toSet().size == length

fun Activity.hideKeyboardBower() {
  currentFocus?.let { view ->
    val immBower = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    immBower?.hideSoftInputFromWindow(view.windowToken, 0)
  }
}

fun Context.checkForPermissionsBower() {
  Dexter.withContext(this)
    .withPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
    .withListener(object : MultiplePermissionsListener {
      override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {}

      override fun onPermissionRationaleShouldBeShown(
        list: List<PermissionRequest>,
        permissionToken: PermissionToken
      ) {
      }
    }).check()
}