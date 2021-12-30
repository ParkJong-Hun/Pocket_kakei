package com.parkjonghun.pocket_kakei.view.recylcerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjonghun.pocket_kakei.databinding.ItemDayOfMonthBinding
import com.parkjonghun.pocket_kakei.model.Sheet

class DayOfMonthAdapter: ListAdapter<Sheet, DayOfMonthAdapter.DayOfMonthViewHolder>(DiffCallback()) {
    inner class DayOfMonthViewHolder(private val binding: ItemDayOfMonthBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Sheet) {
            binding.itemDayOfMonthDescription.text = item.description
            binding.itemDayOfMonthCategory.text = item.category
            binding.itemDayOfMonthMoney.text = item.money.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayOfMonthViewHolder {
        val binding = ItemDayOfMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayOfMonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayOfMonthViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallback: DiffUtil.ItemCallback<Sheet>() {
    override fun areItemsTheSame(oldItem: Sheet, newItem: Sheet): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Sheet, newItem: Sheet): Boolean =
        oldItem == newItem
}