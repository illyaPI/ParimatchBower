package com.poks.par.mar.appp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.poks.par.mar.appp.R
import com.poks.par.mar.appp.databinding.ItemTurnBinding
import com.poks.par.mar.appp.models.Turn

class TurnAdapter : ListAdapter<Turn, TurnAdapter.TurnViewHolder>(DIFF_CALLBACK) {

  override fun getItemId(position: Int): Long = getItem(position).number.toLong()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TurnViewHolder = TurnViewHolder.from(parent)

  override fun onBindViewHolder(holder: TurnViewHolder, position: Int) {
    val turn = getItem(position)
    holder.bind(turn)
  }

  class TurnViewHolder private constructor(
    private val bnd: ItemTurnBinding
  ) : RecyclerView.ViewHolder(bnd.root) {

    fun bind(turn: Turn) {
      bnd.apply {
        textViewNumber.text = root.context.getString(R.string.number, turn.number)
        textViewGuess.text = root.context.getString(R.string.your_guess, turn.guess)
        textViewBullsCows.text = root.context.getString(R.string.bulls_cows, turn.bulls, turn.cows)
      }
    }

    companion object {
      fun from(parent: ViewGroup) = TurnViewHolder(
        ItemTurnBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
    }
  }

  companion object {
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Turn>() {
      override fun areItemsTheSame(oldTurn: Turn, newTurn: Turn): Boolean = oldTurn.number == newTurn.number

      override fun areContentsTheSame(oldTurn: Turn, newTurn: Turn): Boolean = oldTurn == newTurn
    }
  }
}