package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.FragmentDayBinding
import com.parkjonghun.pocket_kakei.model.Sheet
import com.parkjonghun.pocket_kakei.view.activity.AddActivity
import com.parkjonghun.pocket_kakei.view.viewpager.ViewPagerAdapter
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import java.util.*

class DayFragment: Fragment() {
    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDayBinding.inflate(inflater, container, false)



        val viewModel: MainViewModel by activityViewModels()



        val addedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_add_money, null)
        val paidMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_pay_money, null)
        val usedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_use_money, null)

        val layouts: List<LinearLayout> = listOf(view.daySundayLayout, view.dayMondayLayout, view.dayTuesdayLayout, view.dayWednesdayLayout, view.dayThursdayLayout, view.dayFridayLayout, view.daySaturdayLayout)
        val images: List<ImageView> = listOf(view.daySundayDrawable, view.dayMondayDrawable, view.dayTuesdayDrawable, view.dayWednesdayDrawable, view.dayThursdayDrawable, view.dayFridayDrawable, view.daySaturdayDrawable)
        val values: List<TextView> = listOf(view.daySundayValue, view.dayMondayValue, view.dayTuesdayValue, view.dayWednesdayValue, view.dayThursdayValue, view.dayFridayValue, view.daySaturdayValue)

        //AddActivityからデータを追加したら
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == AppCompatActivity.RESULT_OK) {
                //データ更新
                viewModel.loadSheets()
            }
        }


        val pagerAdapter = ViewPagerAdapter(requireActivity())
        pagerAdapter.addFragment(EmptyFragment())
        pagerAdapter.addFragment(DayFragmentArticle(activityResult))
        pagerAdapter.addFragment(EmptyFragment())
        view.dayRestViewPager.adapter = pagerAdapter
        //メイン画面を主に左右にビューがあるようにする
        view.dayRestViewPager.currentItem = 1
        view.dayRestViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(view.dayRestViewPager.currentItem == 0) {
                    viewModel.selectPreviousDay()
                    view.dayRestViewPager.currentItem = 1
                } else if(view.dayRestViewPager.currentItem == 2) {
                    viewModel.selectNextDay()
                    view.dayRestViewPager.currentItem = 1
                }
            }
        })


        //UI更新
        fun initWeekUI() {
            for (i in 0 until 7) {
                layouts[i].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                images[i].visibility = View.INVISIBLE
            }
        }
        fun updateTopUI() {
            view.dayIncomeValue.text = "${viewModel.getSelectedDayIncomeMoney()}"
            view.dayExpenditureValue.text = "${viewModel.getSelectedDayExpenditureMoney()}"
            view.dayExpenditureCashValue.text = "${viewModel.getSelectedDayCashExpenditureMoney()}"
            view.dayExpenditureCardValue.text = "${viewModel.getSelectedDayCardExpenditureMoney()}"
        }
        fun updateCalendarUI() {
            val week = viewModel.loadOneWeekCalendar()
            //支出、収入日の背景
            val addedDay: List<Sheet>? = viewModel.sheets.value?.filter {value ->  value.isAdd && week.map { it.get(Calendar.DATE) }.contains(value.date.get(Calendar.DATE)) }
            val paidDay: List<Sheet>? = viewModel.sheets.value?.filter {value ->  !value.isAdd && week.map { it.get(Calendar.DATE) }.contains(value.date.get(Calendar.DATE)) }

            fun updateEachCalendarUI(days: List<Sheet>?, icon:Drawable) {
                val dayOfWeekList = days?.map { it.date.get(Calendar.DAY_OF_WEEK) }
                if (dayOfWeekList != null) {
                    for (day in dayOfWeekList) {
                        images[day.minus(1)].apply {
                            setImageDrawable(icon)
                            visibility = View.VISIBLE
                        }
                    }
                }
            }

            updateEachCalendarUI(addedDay, addedMoneyIcon)
            updateEachCalendarUI(paidDay, paidMoneyIcon)

            //データが支出と収入どっちもあったら
            if(addedDay != null && paidDay != null) {
                val union = addedDay + paidDay
                val intersection = union.groupBy { it.date.get(Calendar.DATE) }.filter { it.value.size > 1 }.flatMap { it.value }.distinct()
                updateEachCalendarUI(intersection, usedMoneyIcon)
            }
        }
        fun updateSelectCalendarUI() {
            val currentWeek = viewModel.loadOneWeekDayOfMonth()
            if (currentWeek.size == 7) {
                for(i in 0 until 7) {
                    values[i].text = "${currentWeek[i]}"
                }
            }

            val index = viewModel.selectedDay.value?.calendar?.get(Calendar.DAY_OF_WEEK)?.minus(1)
            layouts[index?: 0].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
        }


        //選択した日が変わったら
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            initWeekUI()
            updateCalendarUI()
            updateSelectCalendarUI()
            updateTopUI()
        }
        //モデルが変わったら
        viewModel.sheets.observe(viewLifecycleOwner) {
            initWeekUI()
            updateCalendarUI()
            updateSelectCalendarUI()
            updateTopUI()
        }


        //FloatingButtonクリックしたら
        view.addButtonDay.setOnClickListener {
            if(viewModel.selectedDay.value != null) {
                Intent(activity, AddActivity::class.java).apply {
                    putExtra("calendar", viewModel.calendarDayToString(viewModel.selectedDay.value!!.calendar))
                    activityResult.launch(this)
                }
            }
        }

        return view.root
    }
}