package com.poks.par.mar.appp.uibower.fragmentsbower

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.isDigitsOnly
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import com.poks.par.mar.appp.important.modelsbower.UserBower
import com.poks.par.mar.appp.uibower.activitiesbower.MainActivityBower
import kotlinx.android.synthetic.main.fragment_create_profile_bower.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class CreateProfileFragmentBower : Fragment(R.layout.fragment_create_profile_bower) {

  @Inject
  lateinit var repositoryBower: RepositoryBower
  private var bitmapBower: Bitmap? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    injectBower()
    setupTextWatchersBower()
    setupClickListenersBower()
  }

  private fun setupTextWatchersBower() {
    button_create_bower.isEnabled = validateInput()
    val afterTextChangedBower = { _: Editable? -> button_create_bower.isEnabled = validateInput() }
    edit_text_name_bower.addTextChangedListener(afterTextChanged = afterTextChangedBower)
    edit_text_email_bower.addTextChangedListener(afterTextChanged = afterTextChangedBower)
    edit_text_age_bower.addTextChangedListener(afterTextChanged = afterTextChangedBower)
  }

  private fun validateInput(): Boolean {
    val isAvatarValidBower = bitmapBower != null

    val nameBower = edit_text_name_bower.text
    val isNameValidBower = !nameBower.isNullOrBlank() && nameBower.count() { !it.isWhitespace() } in 3..20
    edit_text_layout_name_bower.error = if (isNameValidBower) null else "name length must be between 3 and 20 characters"

    val emailBower = edit_text_email_bower.text
    val isEmailValidBower = !emailBower.isNullOrBlank() && emailBower.count() { !it.isWhitespace() } in 5..20
    edit_text_layout_email_bower.error = if (isEmailValidBower) null else "email length must be between 5 and 20 characters"

    val ageBower = edit_text_age_bower.text
    val isAgeValidBower = !ageBower.isNullOrBlank() && ageBower.count() { !it.isWhitespace() } in 1..3 && ageBower.isDigitsOnly() && ageBower[0] != '0'
    edit_text_layout_age_bower.error = if (isAgeValidBower) null else "age length must be between 1 and 3 digits"

    return isAvatarValidBower && isNameValidBower && isEmailValidBower && isAgeValidBower
  }

  private fun setupClickListenersBower() {
    image_view_back_bower.setOnClickListener { requireActivity().onBackPressed() }

    image_view_avatar_bower.setOnClickListener {
      startActivityForImage.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
    }

    button_create_bower.setOnClickListener {
      lifecycleScope.launchWhenCreated {
        button_create_bower.isEnabled = false
        withContext(Dispatchers.IO) {
          val bosBower = ByteArrayOutputStream()
          bitmapBower!!.compress(Bitmap.CompressFormat.JPEG, 100, bosBower)
          val byteArrayBower = bosBower.toByteArray()
          bosBower.close()

          val fileBower = File(requireContext().filesDir, "avatar.jpeg").apply { createNewFile() }
          val fosBower = FileOutputStream(fileBower)
          fosBower.write(byteArrayBower)
          fosBower.close()

          val userBower = UserBower(
            nameBower = edit_text_name_bower.text!!.toString().filter { !it.isWhitespace() },
            emailBower = edit_text_email_bower.text!!.toString().filter { !it.isWhitespace() },
            ageBower = edit_text_age_bower.text!!.toString().toInt(),
            avatarBower = fileBower.path
          )
          repositoryBower.userBowerBower = userBower
          Log.i(TAG_BOWER, "setupClickListeners: user: $userBower")
        }
        requireActivity().onBackPressed()
      }
    }
  }

  private val startActivityForImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    val uriBower = it.data?.data
    if (it.resultCode != Activity.RESULT_OK || uriBower == null) return@registerForActivityResult

    bitmapBower =
      if (Build.VERSION.SDK_INT < 28) MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uriBower)
      else ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, uriBower))

    image_view_avatar_bower.load(bitmapBower)
    button_create_bower.isEnabled = validateInput()
  }

  override fun onResume() {
    super.onResume()
    if (repositoryBower.isMusicOnBower) (requireActivity() as MainActivityBower).mediaPlayerBower.start()
  }

  private fun injectBower(): Unit = (requireActivity().application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "CreateProfileFragment"
  }
}