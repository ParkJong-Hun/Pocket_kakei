package com.parkjonghun.pocket_kakei.model

import androidx.room.*
import java.util.*

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

    //TODO: オブジェクトが違っても同じ日だったら同じ扱いする
    @Query("SELECT * FROM Sheet WHERE date = :day")
    fun readThatDay(day: Calendar): List<Sheet>
}