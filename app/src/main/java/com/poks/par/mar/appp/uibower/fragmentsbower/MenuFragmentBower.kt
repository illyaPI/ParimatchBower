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
import com.poks.par.mar.appp.pampdebower.utilbower.checkForPermissionsBower
import kotlinx.android.synthetic.main.fragment_menu_bower.*
import java.io.File
import javax.inject.Inject

class MenuFragmentBower : Fragment(R.layout.fragment_menu_bower) {

  @Inject
  lateinit var repositoryBower: RepositoryBower

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    injectBower()
    requireContext().checkForPermissionsBower()
    setupViewsBower()
    setupClickListenersBower()
  }

  private fun setupViewsBower() {
    val userBower = repositoryBower.userBowerBower
    if (userBower != null) {
      image_view_avatar_bower.load(File(userBower.avatarBower)) { placeholder(R.color.primary) }
    }
    button_music_bower.setText(if (repositoryBower.isMusicOnBower) R.string.music_on else R.string.music_off)
  }

  private fun setupClickListenersBower() {
    val menuItemOnClickListenerBower = View.OnClickListener {
      val navControllerBower = findNavController()
      when {
        it.id == button_music_bower.id -> {
          repositoryBower.isMusicOnBower = !repositoryBower.isMusicOnBower
          button_music_bower.setText(if (repositoryBower.isMusicOnBower) R.string.music_on else R.string.music_off)
          val activityBower = requireActivity() as MainActivityBower
          if (repositoryBower.isMusicOnBower) activityBower.mediaPlayerBower.start() else activityBower.mediaPlayerBower.pause()
        }
        repositoryBower.userBowerBower == null -> navControllerBower.navigate(R.id.action_menuFragment_to_createProfileFragment)
        it.id == image_view_avatar_bower.id -> navControllerBower.navigate(R.id.action_menuFragment_to_profileFragment)
        it.id == button_play_bower.id -> navControllerBower.navigate(R.id.action_menuFragment_to_gameFragment)
        it.id == button_profile_bower.id -> navControllerBower.navigate(R.id.action_menuFragment_to_profileFragment)
      }
    }
    image_view_avatar_bower.setOnClickListener(menuItemOnClickListenerBower)
    button_play_bower.setOnClickListener(menuItemOnClickListenerBower)
    button_profile_bower.setOnClickListener(menuItemOnClickListenerBower)
    button_music_bower.setOnClickListener(menuItemOnClickListenerBower)
  }

  override fun onResume() {
    super.onResume()
    if (repositoryBower.isMusicOnBower) (requireActivity() as MainActivityBower).mediaPlayerBower.start()
  }

  private fun injectBower(): Unit = (requireActivity().application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "MenuFragment"
  }
}