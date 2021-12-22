package com.parkjonghun.pocket_kakei.model

import java.util.*

data class SheetModel(
    var date:Calendar = Calendar.getInstance(),
    var isAdd:Boolean = false,
    var money:Int = 0,
    var category:String = "none"
) {
}