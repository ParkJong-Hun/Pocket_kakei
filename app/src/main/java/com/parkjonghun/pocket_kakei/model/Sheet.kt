package com.parkjonghun.pocket_kakei.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sheet(
    @PrimaryKey val id: String,
    val date: Long,
    val isAdd: Boolean,
    val money: Int,
    val category: String
)