package com.poks.par.mar.appp.uibower.activitiesbower

import android.media.MediaPlayer
import android.os.Bundle
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.applicationbower.GameApplicationBower
import com.poks.par.mar.appp.databinding.ActivityMainBowerBinding
import com.poks.par.mar.appp.applicationbower.databower.RepositoryBower
import com.poks.par.mar.appp.pampdebower.utilbower.ViewBindingActivityBower
import javax.inject.Inject

class MainActivityBower : ViewBindingActivityBower<ActivityMainBowerBinding>(ActivityMainBowerBinding::inflate) {

  @Inject
  lateinit var repositoryBower: RepositoryBower
  lateinit var mediaPlayerBower: MediaPlayer

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    injectBower()
    setupMediaPlayerBower()
  }

  private fun setupMediaPlayerBower() {
    mediaPlayerBower = MediaPlayer.create(this, R.raw.background_song_bower)
    mediaPlayerBower.isLooping = true
    mediaPlayerBower.setVolume(0.3F, 0.3F)
  }

  override fun onPause() {
    super.onPause()
    if (mediaPlayerBower.isPlaying) mediaPlayerBower.pause()
  }

  override fun onDestroy() {
    super.onDestroy()
    mediaPlayerBower.release()
  }

  private fun injectBower(): Unit = (application as GameApplicationBower).appComponentBower.inject(this)

  companion object {
    private const val TAG_BOWER: String = "MainActivity"
  }
}