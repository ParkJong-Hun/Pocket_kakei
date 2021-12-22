package com.parkjonghun.pocket_kakei.view.decorator

import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class BackgroundDecorator(
    private val drawable: Drawable,
    private val addedDay: List<Int>
    ): DayViewDecorator {

    private val calendar = Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        day?.copyTo(calendar)
        val monthDay = calendar.get(Calendar.DAY_OF_MONTH)
        return addedDay.contains(monthDay)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setBackgroundDrawable(drawable)
    }
}