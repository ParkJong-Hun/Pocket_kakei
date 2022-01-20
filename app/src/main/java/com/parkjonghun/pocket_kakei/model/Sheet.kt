package com.parkjonghun.pocket_kakei.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

//Model
@Entity
data class Sheet(
    @PrimaryKey val id: String,
    val date: Calendar,
    val isAdd: Boolean,
    val money: Int,
    val category: String,
    val description: String,
    val memo: String
): Serializable