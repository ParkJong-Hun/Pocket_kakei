package com.parkjonghun.pocket_kakei.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.model.AppDatabase
import com.parkjonghun.pocket_kakei.model.Sheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*

class AddViewModel(application: Application):AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    //カレンダー
    val calendar: Calendar = Calendar.getInstance()
    //金額
    private var _moneyValue = 0
    private var _moneyValueLiveData: MutableLiveData<Int> = MutableLiveData()
    val moneyValue:LiveData<Int> = _moneyValueLiveData
    //収入か支出か
    var isAdd: Boolean = false
    //説明
    var description: String = ""
    //カテゴリー
    var category: String = "deposit"
    //シート追加の画面のステップ
    private var _currentStep = 0
    var currentStep:MutableLiveData<Int> = MutableLiveData()
    //データ追加準備完了合図
    private var _dataIsReady: Boolean = false
    var dataIsReady: MutableLiveData<Boolean> = MutableLiveData()
    //メモ
    private val memo: String = ""


    //3字があれば「,」を記入
    fun checkComma(value:Int): String {
        val formatter = DecimalFormat("###,###")
        _moneyValue = value
        _moneyValueLiveData.value = _moneyValue
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


    //次の画面に移る
    fun nextStep() {
        _currentStep += 1
        currentStep.value = _currentStep
    }
    //以前の画面に戻る
    fun previousStep() {
        _currentStep -= 1
        currentStep.value = _currentStep
    }


    //データを追加する準備OK
    fun dataReady() {
        _dataIsReady = true
        dataIsReady.value = _dataIsReady
    }
    //データをデータベースに挿入
    fun addOnDatabase() {
        val db = AppDatabase.getInstance(context)
        val newSheet = moneyValue.value?.let {
            Sheet(
                id =  calendar.time.toString() + "_" + description,
                date = calendar,
                isAdd = isAdd,
                money = it,
                category = category,
                description = description,
                memo = memo
            )
        }
        if (newSheet != null) {
            CoroutineScope(Dispatchers.IO).launch {
                db!!.sheetDao().insert(newSheet)
            }
        }
    }


    //選択した日付を返す
    fun getToday(): String {
        return "${calendar.get(Calendar.YEAR)}${context.resources.getString(R.string.year)} ${calendar.get(Calendar.MONTH) + 1}${context.resources.getString(R.string.month)} ${calendar.get(Calendar.DAY_OF_MONTH)}${context.resources.getString(R.string.day)}"
    }
}