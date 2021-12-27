package com.parkjonghun.pocket_kakei.model

import androidx.room.*

@Dao
interface SheetDao {
    @Insert
    fun insert(sheet: Sheet?)

    @Update
    fun update(sheet:Sheet)

    @Delete
    fun delete(sheet:Sheet)

    @Query("SELECT * FROM Sheet")
    fun load(): List<Sheet>
}