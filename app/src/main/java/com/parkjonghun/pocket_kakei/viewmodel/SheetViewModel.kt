package com.parkjonghun.pocket_kakei.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjonghun.pocket_kakei.model.AppDatabase
import com.parkjonghun.pocket_kakei.model.Sheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.DecimalFormat

class SheetViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    //Roomデータベース
    private val db = AppDatabase.getInstance(context)


    //金額
    private var _moneyValue = 0
    private var _moneyValueLiveData: MutableLiveData<Int> = MutableLiveData()
    val moneyValue: LiveData<Int> = _moneyValueLiveData


    //3字があれば「,」を記入
    fun checkComma(value:Int): String {
        val formatter = DecimalFormat("###,###")
        _moneyValue = value
        _moneyValueLiveData.value = _moneyValue
        Log.d("money", "money: ${moneyValue.value}")
        return formatter.format(value)
    }
    //数字の長さが9より高いと禁止
    fun checkLength(value:String): String {
        return if (value.filter { it.isDigit() }.length > 9) {
            value.drop(1)
        } else if (value.filter { it.isDigit() }.isEmpty()) {
            "0"
        } else {
            value
        }
    }

    //シート削除
    suspend fun deleteSheet(sheet: Sheet) {
        CoroutineScope(Dispatchers.IO).async {
            db?.sheetDao()?.delete(sheet)
        }.join()
    }
    //シート更新
    suspend fun updateSheet(sheet: Sheet) {
        CoroutineScope(Dispatchers.IO).async {
            db?.sheetDao()?.update(sheet)
        }.join()
    }
}