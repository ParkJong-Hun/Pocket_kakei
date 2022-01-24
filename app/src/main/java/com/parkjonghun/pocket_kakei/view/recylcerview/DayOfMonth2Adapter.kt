package com.parkjonghun.pocket_kakei.view.recylcerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.ItemDayOfMonth2Binding
import com.parkjonghun.pocket_kakei.model.Sheet

class DayOfMonth2Adapter: ListAdapter<Sheet, DayOfMonth2Adapter.DayOfMonth2ViewHolder>(DiffCallback()) {
    interface OnItemCLickListener {
        fun onItemClick(v: View, sheet: Sheet)
    }

    private lateinit var itemClickListener: OnItemCLickListener

    inner class DayOfMonth2ViewHolder(private val binding: ItemDayOfMonth2Binding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Sheet) {
            //Sheetの情報を加工してアイテムのUIを更新
            binding.itemDayOfMonth2Description.text = item.description
            when(item.category) {
                "deposit"-> binding.itemDayOfMonth2Category.text = itemView.context.resources.getString(
                    R.string.deposit)
                "cash" -> binding.itemDayOfMonth2Category.text = itemView.context.resources.getString(
                    R.string.cash)
                "debitCard" -> binding.itemDayOfMonth2Category.text = itemView.context.resources.getString(
                    R.string.debit_card)
                "creditCard" -> binding.itemDayOfMonth2Category.text = itemView.context.resources.getString(
                    R.string.credit_card)
            }
            binding.itemDayOfMonth2Money.text = "${item.money} ${itemView.context.resources.getString(
                R.string.currency)}"


            //クリックリスナー
            itemView.setOnClickListener{
                itemClickListener.onItemClick(it, item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayOfMonth2ViewHolder {
        val binding = ItemDayOfMonth2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayOfMonth2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayOfMonth2ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setOnClickListener(onItemClickListener: OnItemCLickListener) {
        itemClickListener = onItemClickListener
    }
}