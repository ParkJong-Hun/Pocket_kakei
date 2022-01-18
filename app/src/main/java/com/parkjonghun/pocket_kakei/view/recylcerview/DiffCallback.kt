package com.parkjonghun.pocket_kakei.view.recylcerview

import androidx.recyclerview.widget.DiffUtil
import com.parkjonghun.pocket_kakei.model.Sheet

class DiffCallback: DiffUtil.ItemCallback<Sheet>() {
    override fun areItemsTheSame(oldItem: Sheet, newItem: Sheet): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Sheet, newItem: Sheet): Boolean =
        oldItem == newItem
}