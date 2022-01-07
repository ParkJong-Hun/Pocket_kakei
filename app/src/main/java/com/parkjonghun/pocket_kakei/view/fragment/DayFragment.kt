package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
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
        //TODO: forを使ってもっと見やすく
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            Log.d("", viewModel.selectedDay.value.toString())
            view.daySundayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayMondayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayTuesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayWednesdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayThursdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.dayFridayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            view.daySaturdayLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            when(it.calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> {
                    view.daySundayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) }"
                    view.dayMondayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 1}"
                    view.dayTuesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 2}"
                    view.dayWednesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 3}"
                    view.dayThursdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 4}"
                    view.dayFridayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 5}"
                    view.daySaturdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 6}"
                    view.daySundayLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lifeWhite
                        )
                    )
                }
                2 -> {
                    view.daySundayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 1}"
                    view.dayMondayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH)}"
                    view.dayTuesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 1}"
                    view.dayWednesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 2}"
                    view.dayThursdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 3}"
                    view.dayFridayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 4}"
                    view.daySaturdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 5}"
                    view.dayMondayLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lifeWhite
                        )
                    )
                }
                3 -> {
                    view.daySundayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 2}"
                    view.dayMondayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 1}"
                    view.dayTuesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH)}"
                    view.dayWednesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 1}"
                    view.dayThursdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 2}"
                    view.dayFridayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 3}"
                    view.daySaturdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 4}"
                    view.dayTuesdayLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lifeWhite
                        )
                    )
                }
                4 -> {
                    view.daySundayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 3}"
                    view.dayMondayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 2}"
                    view.dayTuesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 1}"
                    view.dayWednesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH)}"
                    view.dayThursdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 1}"
                    view.dayFridayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 2}"
                    view.daySaturdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 3}"
                    view.dayWednesdayLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lifeWhite
                        )
                    )
                }
                5 -> {
                    view.daySundayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 4}"
                    view.dayMondayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 3}"
                    view.dayTuesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 2}"
                    view.dayWednesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 1}"
                    view.dayThursdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH)}"
                    view.dayFridayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 1}"
                    view.daySaturdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 2}"
                    view.dayThursdayLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lifeWhite
                        )
                    )
                }
                6 -> {
                    view.daySundayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 5}"
                    view.dayMondayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 4}"
                    view.dayTuesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 3}"
                    view.dayWednesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 2}"
                    view.dayThursdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 1}"
                    view.dayFridayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH)}"
                    view.daySaturdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) + 1}"
                    view.dayFridayLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lifeWhite
                        )
                    )
                }
                7 -> {
                    view.daySundayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 6}"
                    view.dayMondayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 5}"
                    view.dayTuesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 4}"
                    view.dayWednesdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 3}"
                    view.dayThursdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 2}"
                    view.dayFridayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH) - 1}"
                    view.daySaturdayValue.text = "${it.calendar.get(Calendar.DAY_OF_MONTH)}"
                    view.daySaturdayLayout.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.lifeWhite
                        )
                    )
                }
            }
        }

        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}