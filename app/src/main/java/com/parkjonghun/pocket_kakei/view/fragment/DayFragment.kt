package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.FragmentDayBinding
import com.parkjonghun.pocket_kakei.view.viewpager.ViewPagerAdapter
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import java.util.*

class DayFragment: Fragment() {
    @SuppressLint("SetTextI18n")
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
        fun initWeekUI() {
            view.daySundayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayMondayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayTuesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayWednesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayThursdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayFridayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.daySaturdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        }
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            initWeekUI()
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
        }


        return view.root
    }
}