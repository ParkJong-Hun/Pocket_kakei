package com.parkjonghun.pocket_kakei.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.parkjonghun.pocket_kakei.model.AppDatabase

class SheetViewModel(application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    //Roomデータベース
    private val db = AppDatabase.getInstance(context)
}