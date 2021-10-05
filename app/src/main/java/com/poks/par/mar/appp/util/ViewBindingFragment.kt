package com.poks.par.mar.appp.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<VB : ViewBinding>(
  private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

  private var _bnd: VB? = null
  protected val bnd: VB
    get() = _bnd!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _bnd = bindingInflater(inflater, container, false)
    return _bnd!!.root
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _bnd = null
  }
}