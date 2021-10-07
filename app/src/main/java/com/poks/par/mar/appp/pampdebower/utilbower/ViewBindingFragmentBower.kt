package com.poks.par.mar.appp.pampdebower.utilbower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragmentBower<VB : ViewBinding>(
  private val bindingInflaterBower: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

  private var _bndBower: VB? = null
  protected val bndBower: VB
    get() = _bndBower!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _bndBower = bindingInflaterBower(inflater, container, false)
    return _bndBower!!.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _bndBower = null
  }
}