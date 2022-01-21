package com.parkjonghun.pocket_kakei.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.parkjonghun.pocket_kakei.model.AppDatabase
import com.parkjonghun.pocket_kakei.model.Sheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SheetViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    //Roomデータベース
    private val db = AppDatabase.getInstance(context)

    suspend fun deleteSheet(sheet: Sheet) {
        CoroutineScope(Dispatchers.IO).async {
            db?.sheetDao()?.delete(sheet)
        }.join()
    }

    fun editSheet() {

    }
}