package com.poks.par.mar.appp.applicationbower.someact.adaptersbower

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.databinding.ItemTurnBowerBinding
import com.poks.par.mar.appp.important.modelsbower.TurnBower

class TurnAdapterBower : ListAdapter<TurnBower, TurnAdapterBower.TurnViewHolder>(DIFF_CALLBACK_BOWER) {

  override fun getItemId(position: Int): Long = getItem(position).numberBower.toLong()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnViewHolder = TurnViewHolder.fromBower(parent)

  override fun onBindViewHolder(holder: TurnViewHolder, position: Int) {
    val turnBower = getItem(position)
    holder.bind(turnBower)
  }

  class TurnViewHolder private constructor(
    private val bndBower: ItemTurnBowerBinding
  ) : RecyclerView.ViewHolder(bndBower.root) {

    fun bind(turnBower: TurnBower) {
      bndBower.apply {
        textViewNumberBower.text = root.context.getString(R.string.number, turnBower.numberBower)
        textViewGuessBower.text = root.context.getString(R.string.your_guess, turnBower.guessBower)
        textViewBullsCowsBower.text = root.context.getString(R.string.bulls_cows, turnBower.bullsBower, turnBower.cowsBower)
      }
    }

    companion object {
      fun fromBower(parentBower: ViewGroup) = TurnViewHolder(
        ItemTurnBowerBinding.inflate(LayoutInflater.from(parentBower.context), parentBower, false)
      )
    }
  }

  companion object {
    val DIFF_CALLBACK_BOWER = object : DiffUtil.ItemCallback<TurnBower>() {
      override fun areItemsTheSame(oldTurnBower: TurnBower, newTurnBower: TurnBower): Boolean = oldTurnBower.numberBower == newTurnBower.numberBower

      override fun areContentsTheSame(oldTurnBower: TurnBower, newTurnBower: TurnBower): Boolean = oldTurnBower == newTurnBower
    }
  }
}