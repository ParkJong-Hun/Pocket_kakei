package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.FragmentMonthBinding
import com.parkjonghun.pocket_kakei.model.Sheet
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
            //支出、収入日の背景
            val addedDay: List<Sheet>? = viewModel.sheets.value?.filter { it.isAdd }
            val paidDay: List<Sheet>? = viewModel.sheets.value?.filter { !it.isAdd }

            val addedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_add_money, null)
            val paidMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_pay_money, null)
            val usedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_use_money, null)

            view.monthCalendar.addDecorators(
                addedDay?.let { it1 -> BackgroundDecorator(addedMoneyIcon, it1) },
                paidDay?.let { it1 -> BackgroundDecorator(paidMoneyIcon, it1) },
            )

            if(addedDay != null && paidDay != null) {
                val union = addedDay + paidDay
                val intersection = union.groupBy { it.date.time }.filter { it.value.size > 1 }.flatMap { it.value }.distinct()
                view.monthCalendar.addDecorator(BackgroundDecorator(usedMoneyIcon, intersection))
            }
        }
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            //TODO: 選択した日のデータを加工してAdapterに伝える
            val sheetsOfSelectedDay = viewModel.optimizeForMonth()
            view.monthRecyclerView.visibility = View.VISIBLE
        }

        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == AppCompatActivity.RESULT_OK) {
                viewModel.loadSheets()
            }
        }

        //FloatingButtonクリックしたら
        view.addButton.setOnClickListener {
            Intent(activity, AddActivity::class.java).apply {
                putExtra("calendar", viewModel.calendarDayToString(view.monthCalendar.selectedDate))
                activityResult.launch(this)
            }
        }
        //今日を選択する
        view.monthCalendar.selectedDate = CalendarDay.today()
        //週末の色
        view.monthCalendar.addDecorators(
            SaturdayDecorator(),
            SundayDecorator(),
        )

        view.monthCalendar.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                viewModel.selectDay(date).also {
                    Log.d("", viewModel.selectedDay.value.toString())
                }
            }
        }
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}