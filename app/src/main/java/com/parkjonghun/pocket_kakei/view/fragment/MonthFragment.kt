package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.FragmentMonthBinding
import com.parkjonghun.pocket_kakei.model.SheetModel
import com.parkjonghun.pocket_kakei.view.activity.AddActivity
import com.parkjonghun.pocket_kakei.view.decorator.BackgroundDecorator
import com.parkjonghun.pocket_kakei.view.decorator.SaturdayDecorator
import com.parkjonghun.pocket_kakei.view.decorator.SundayDecorator
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay

class MonthFragment: Fragment() {
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentMonthBinding.inflate(inflater, container, false)

        val viewModel: MainViewModel by activityViewModels()
        viewModel.sheets.observe(viewLifecycleOwner) {
            //TODO: リストを加工してカレンダーに表示する
            //支出、収入日の背景
            val addedDay:List<SheetModel> = listOf(SheetModel())
            val paidDay:List<SheetModel> = listOf(SheetModel().apply {date.set(2021, 10, 12)})

            val addedMoneyIcon:Drawable = view.root.resources.getDrawable(R.drawable.ic_add_money, null)
            val paidMoneyIcon:Drawable = view.root.resources.getDrawable(R.drawable.ic_pay_money, null)

            view.monthCalendar.addDecorators(
                BackgroundDecorator(addedMoneyIcon, addedDay),
                BackgroundDecorator(paidMoneyIcon, paidDay)
            )
        }

        //FloatingButtonクリックしたら
        view.addButton.setOnClickListener {
            Intent(activity, AddActivity::class.java).apply {
                putExtra("calendar", viewModel.calendarDayToString(view.monthCalendar.selectedDate))
                startActivity(this)
            }
        }
        //今日を選択する
        view.monthCalendar.selectedDate = CalendarDay.today()
        //週末の色
        view.monthCalendar.addDecorators(
            SaturdayDecorator(),
            SundayDecorator(),
        )
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}