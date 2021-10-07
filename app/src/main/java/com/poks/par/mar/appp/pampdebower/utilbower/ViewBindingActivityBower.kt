package com.poks.par.mar.appp.pampdebower.utilbower

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class ViewBindingActivityBower<VB : ViewBinding>(
  private val bindingInflaterBower: (LayoutInflater) -> VB
) : AppCompatActivity() {

  protected lateinit var bndBower: VB

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    bndBower = bindingInflaterBower(layoutInflater)
    setContentView(bndBower.root)
  }
}