package com.poks.par.mar.appp.uibower.fragmentsbower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import com.poks.par.mar.appp.uibower.activitiesbower.MainActivityBower
import kotlinx.android.synthetic.main.fragment_profile_bower.*
import java.io.File
import javax.inject.Inject

class ProfileFragmentBower : Fragment(R.layout.fragment_profile_bower) {

  @Inject
  lateinit var repositoryBower: RepositoryBower

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    injectBower()
    setupViewsBower()
    setupClickListenersBower()
  }

  private fun setupViewsBower() {
    val userBower = repositoryBower.userBowerBower!!
    image_view_avatar_bower.load(File(userBower.avatarBower)) { placeholder(R.color.primary) }
    text_view_name_bower.text = userBower.nameBower
    text_view_email_bower.text = userBower.emailBower
    text_view_age_bower.text = userBower.ageBower.toString()
  }

  private fun setupClickListenersBower() {
    image_view_back_bower.setOnClickListener { requireActivity().onBackPressed() }

    text_view_privacy_policy_bower.setOnClickListener {
      findNavController().navigate(R.id.action_profileFragment_to_privacyPolicyFragment)
    }
  }

  override fun onResume() {
    super.onResume()
    if (repositoryBower.isMusicOnBower) (requireActivity() as MainActivityBower).mediaPlayerBower.start()
  }

  private fun injectBower(): Unit = (requireActivity().application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "ProfileFragment"
  }
}