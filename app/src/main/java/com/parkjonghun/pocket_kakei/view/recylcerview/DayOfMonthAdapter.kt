package com.parkjonghun.pocket_kakei.view.recylcerview

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.ItemDayOfMonthBinding
import com.parkjonghun.pocket_kakei.model.Sheet

class DayOfMonthAdapter: ListAdapter<Sheet, DayOfMonthAdapter.DayOfMonthViewHolder>(DiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(v: View, sheet: Sheet)
    }

    private lateinit var itemClickListener: OnItemClickListener

    inner class DayOfMonthViewHolder(private val binding: ItemDayOfMonthBinding): RecyclerView.ViewHolder(binding.root) {
        val context: Context = binding.root.context

        @SuppressLint("SetTextI18n")
        fun bind(item: Sheet) {
            //Sheetの情報を加工してアイテムのUIを更新
            binding.itemDayOfMonthDescription.text = item.description
            when(item.category) {
                "deposit"-> binding.itemDayOfMonthCategory.text = itemView.context.resources.getString(
                    R.string.deposit)
                "cash" -> binding.itemDayOfMonthCategory.text = itemView.context.resources.getString(
                    R.string.cash)
                "debitCard" -> binding.itemDayOfMonthCategory.text = itemView.context.resources.getString(
                    R.string.debit_card)
                "creditCard" -> binding.itemDayOfMonthCategory.text = itemView.context.resources.getString(
                    R.string.credit_card)
            }
            binding.itemDayOfMonthMoney.text = "${item.money} ${itemView.context.resources.getString(
                R.string.currency)}"
            if(item.isAdd) {
                binding.itemDayOfMonthMoney.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
            } else {
                binding.itemDayOfMonthMoney.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
            }


            //クリックリスナー
            itemView.setOnClickListener{
                itemClickListener.onItemClick(it, item)
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

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }
}