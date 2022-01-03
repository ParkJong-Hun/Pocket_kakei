package com.parkjonghun.pocket_kakei.view.decorator

import android.graphics.drawable.Drawable
import com.parkjonghun.pocket_kakei.model.Sheet
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class BackgroundDecorator(
    private val drawable: Drawable,
    private val addedDay: List<Sheet>
    ): DayViewDecorator {

    private val calendar = Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        //シートにある日なら
        day?.copyTo(calendar)
        val yearDay = calendar.get(Calendar.DAY_OF_YEAR)
        return addedDay.map{ it.date.get(Calendar.DAY_OF_YEAR) }.contains(yearDay)
    }

    override fun decorate(view: DayViewFacade?) {
        //背景をdrawableにイメージを変える
        view?.setBackgroundDrawable(drawable)
    }
}