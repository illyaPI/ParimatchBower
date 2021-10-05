package com.poks.par.mar.appp.ui.fragments

import android.os.Bundle
import android.view.View
import com.poks.par.mar.appp.application.GameApplication
import com.poks.par.mar.appp.data.Repository
import com.poks.par.mar.appp.databinding.FragmentProfileBinding
import com.poks.par.mar.appp.util.ViewBindingFragment
import javax.inject.Inject

class ProfileFragment : ViewBindingFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

  @Inject
  lateinit var repository: Repository

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    inject()
  }

  private fun inject(): Unit = (requireActivity().application as GameApplication).appComponent.inject(this)

  companion object {
    private const val TAG: String = "ProfileFragment"
  }
}