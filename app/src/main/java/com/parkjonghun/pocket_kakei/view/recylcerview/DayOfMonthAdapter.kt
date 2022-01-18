package com.parkjonghun.pocket_kakei.view.recylcerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.ItemDayOfMonthBinding
import com.parkjonghun.pocket_kakei.model.Sheet

class DayOfMonthAdapter: ListAdapter<Sheet, DayOfMonthAdapter.DayOfMonthViewHolder>(DiffCallback()) {
    inner class DayOfMonthViewHolder(private val binding: ItemDayOfMonthBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Sheet) {
            //Sheetの情報を加工してアイテムのUIを更新
            binding.itemDayOfMonthDescription.text = item.description
            when(item.category) {
                "deposit"-> binding.itemDayOfMonthCategory.text = "入金"
                "cash" -> binding.itemDayOfMonthCategory.text = "現金"
                "debitCard" -> binding.itemDayOfMonthCategory.text = "デビットカード"
                "creditCard" -> binding.itemDayOfMonthCategory.text = "クレジットカード"
            }
            binding.itemDayOfMonthMoney.text = "${item.money} 円"
            if(item.isAdd) {
                binding.itemDayOfMonthMoney.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
            } else {
                binding.itemDayOfMonthMoney.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
            }
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