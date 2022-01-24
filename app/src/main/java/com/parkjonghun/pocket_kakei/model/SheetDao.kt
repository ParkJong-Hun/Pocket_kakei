package com.parkjonghun.pocket_kakei.model

import androidx.room.*

@Dao
interface SheetDao {
    //データ挿入
    @Insert
    fun insert(sheet: Sheet?)
    //データアップデート
    @Update
    fun update(sheet:Sheet)
    //データ削除
    @Delete
    fun delete(sheet:Sheet)
    //データロード
    @Query("SELECT * FROM Sheet")
    fun load(): List<Sheet>
    //該当する日のデータをロード
    @Query("SELECT * FROM Sheet WHERE id LIKE :day")
    fun readThatDay(day: String): List<Sheet>
}