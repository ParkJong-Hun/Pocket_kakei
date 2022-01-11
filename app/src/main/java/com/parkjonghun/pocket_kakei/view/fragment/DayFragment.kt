package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.FragmentDayBinding
import com.parkjonghun.pocket_kakei.model.Sheet
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



        val SUN = 0
        val MON = 1
        val TUE = 2
        val WED = 3
        val THU = 4
        val FRI = 5
        val SAT = 6

        val addedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_add_money, null)
        val paidMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_pay_money, null)
        val usedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_use_money, null)



        val pagerAdapter = ViewPagerAdapter(requireActivity())
        pagerAdapter.addFragment(DayFragmentMargin())
        pagerAdapter.addFragment(DayFragmentArticle())
        pagerAdapter.addFragment(DayFragmentMargin())
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
            view.daySundayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayMondayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayTuesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayWednesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayThursdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayFridayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.daySaturdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.daySundayDrawable.visibility = View.INVISIBLE
            view.dayMondayDrawable.visibility = View.INVISIBLE
            view.dayTuesdayDrawable.visibility = View.INVISIBLE
            view.dayWednesdayDrawable.visibility = View.INVISIBLE
            view.dayThursdayDrawable.visibility = View.INVISIBLE
            view.dayFridayDrawable.visibility = View.INVISIBLE
            view.daySaturdayDrawable.visibility = View.INVISIBLE
        }
        fun updateTopUI() {
            view.dayIncomeValue.text = "${viewModel.getSelectedDayIncomeMoney()}"
            view.dayExpenditureValue.text = "${viewModel.getSelectedDayExpenditureMoney()}"
            view.dayExpenditureCashValue.text = "${viewModel.getSelectedDayCashExpenditureMoney()}"
            view.dayExpenditureCardValue.text = "${viewModel.getSelectedDayCardExpenditureMoney()}"
        }
        fun updateEachCalendarUI(days: List<Sheet>?, icon:Drawable) {
            val dayOfWeekList = days?.map { it.date.get(Calendar.DAY_OF_WEEK) }
            if (dayOfWeekList != null) {
                for (day in dayOfWeekList) {
                    when (day) {
                        SUN + 1 -> {
                            view.daySundayDrawable.setImageDrawable(icon)
                            view.daySundayDrawable.visibility = View.VISIBLE
                        }
                        MON + 1 -> {
                            view.dayMondayDrawable.setImageDrawable(icon)
                            view.dayMondayDrawable.visibility = View.VISIBLE
                        }
                        TUE + 1 -> {
                            view.dayTuesdayDrawable.setImageDrawable(icon)
                            view.dayTuesdayDrawable.visibility = View.VISIBLE
                        }
                        WED + 1 -> {
                            view.dayWednesdayDrawable.setImageDrawable(icon)
                            view.dayWednesdayDrawable.visibility = View.VISIBLE
                        }
                        THU + 1 -> {
                            view.dayThursdayDrawable.setImageDrawable(icon)
                            view.dayThursdayDrawable.visibility = View.VISIBLE
                        }
                        FRI + 1 -> {
                            view.dayFridayDrawable.setImageDrawable(icon)
                            view.dayFridayDrawable.visibility = View.VISIBLE
                        }
                        SAT + 1 -> {
                            view.daySaturdayDrawable.setImageDrawable(icon)
                            view.daySaturdayDrawable.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        fun updateCalendarUI() {
            val week = viewModel.loadOneWeekCalendar()
            //支出、収入日の背景
            val addedDay: List<Sheet>? = viewModel.sheets.value?.filter {value ->  value.isAdd && week.map { it.get(Calendar.DATE) }.contains(value.date.get(Calendar.DATE)) }
            val paidDay: List<Sheet>? = viewModel.sheets.value?.filter {value ->  !value.isAdd && week.map { it.get(Calendar.DATE) }.contains(value.date.get(Calendar.DATE)) }

            updateEachCalendarUI(addedDay, addedMoneyIcon)
            updateEachCalendarUI(paidDay, paidMoneyIcon)

            //データが支出と収入どっちもあったら
            if(addedDay != null && paidDay != null) {
                val union = addedDay + paidDay
                val intersection = union.groupBy { it.date.get(Calendar.DATE) }.filter { it.value.size > 1 }.flatMap { it.value }.distinct()
                updateEachCalendarUI(intersection, usedMoneyIcon)
            }
        }

        //選択した日が変わったら
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            initWeekUI()
            updateCalendarUI()

            val currentWeek = viewModel.loadOneWeekDayOfMonth()
            if (currentWeek.size == 7) {
                view.daySundayValue.text = "${currentWeek[SUN]}"
                view.dayMondayValue.text = "${currentWeek[MON]}"
                view.dayTuesdayValue.text = "${currentWeek[TUE]}"
                view.dayWednesdayValue.text = "${currentWeek[WED]}"
                view.dayThursdayValue.text = "${currentWeek[THU]}"
                view.dayFridayValue.text = "${currentWeek[FRI]}"
                view.daySaturdayValue.text = "${currentWeek[SAT]}"
            }
            
            when(it.calendar.get(Calendar.DAY_OF_WEEK)) {
                SUN + 1 -> view.daySundayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
                MON + 1 -> view.dayMondayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
                TUE + 1 -> view.dayTuesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
                WED + 1 -> view.dayWednesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
                THU + 1 -> view.dayThursdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
                FRI + 1 -> view.dayFridayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
                SAT + 1 -> view.daySaturdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.lifeWhite))
            }

            updateTopUI()
        }
        //モデルが変わったら
        viewModel.sheets.observe(viewLifecycleOwner) {
            updateCalendarUI()
            updateTopUI()
        }

        return view.root
    }
}