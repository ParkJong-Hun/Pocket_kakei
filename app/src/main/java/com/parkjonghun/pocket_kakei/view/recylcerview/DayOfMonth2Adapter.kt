package com.parkjonghun.pocket_kakei.view.recylcerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjonghun.pocket_kakei.databinding.ItemDayOfMonth2Binding
import com.parkjonghun.pocket_kakei.model.Sheet

class DayOfMonth2Adapter: ListAdapter<Sheet, DayOfMonth2Adapter.DayOfMonth2ViewHolder>(DiffCallback()) {
    inner class DayOfMonth2ViewHolder(private val binding: ItemDayOfMonth2Binding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Sheet) {
            //Sheetの情報を加工してアイテムのUIを更新
            binding.itemDayOfMonth2Description.text = item.description
            when(item.category) {
                "deposit"-> binding.itemDayOfMonth2Category.text = "入金"
                "cash" -> binding.itemDayOfMonth2Category.text = "現金"
                "debitCard" -> binding.itemDayOfMonth2Category.text = "デビットカード"
                "creditCard" -> binding.itemDayOfMonth2Category.text = "クレジットカード"
            }
            binding.itemDayOfMonth2Money.text = "${item.money} 円"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayOfMonth2ViewHolder {
        val binding = ItemDayOfMonth2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayOfMonth2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayOfMonth2ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}