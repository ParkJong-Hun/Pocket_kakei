package com.parkjonghun.pocket_kakei.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.parkjonghun.pocket_kakei.databinding.FragmentMonthBinding
import com.parkjonghun.pocket_kakei.view.AddActivity
import com.parkjonghun.pocket_kakei.view.decorator.SaturdayDecorator
import com.parkjonghun.pocket_kakei.view.decorator.SundayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay

class MonthFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentMonthBinding.inflate(inflater, container, false)
        //FloatingButtonクリックしたら
        view.addButton.setOnClickListener {
            startActivity(Intent(activity, AddActivity::class.java))
        }
        //今日を選択する
        view.monthCalendar.selectedDate = CalendarDay.today()
        //週末の色
        view.monthCalendar.addDecorators(
            SaturdayDecorator(),
            SundayDecorator()
        )
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}