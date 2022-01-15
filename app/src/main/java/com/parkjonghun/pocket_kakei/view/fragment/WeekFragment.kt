package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.parkjonghun.pocket_kakei.databinding.FragmentWeekBinding
import com.parkjonghun.pocket_kakei.view.viewpager.ViewPagerAdapter
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import java.util.*

class WeekFragment: Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentWeekBinding.inflate(inflater, container, false)

        val viewModel: MainViewModel by activityViewModels()

        val pagerAdapter = ViewPagerAdapter(requireActivity())
        pagerAdapter.addFragment(EmptyFragment())
        pagerAdapter.addFragment(WeekFragmentArticle())
        pagerAdapter.addFragment(EmptyFragment())
        view.weekViewPager.adapter = pagerAdapter
        view.weekViewPager.currentItem = 1
        //スクロールしたら
        view.weekViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(view.weekViewPager.currentItem == 0) {
                    viewModel.selectPreviousMonth()
                    view.weekViewPager.currentItem = 1
                } else if(view.weekViewPager.currentItem == 2) {
                    viewModel.selectNextMonth()
                    view.weekViewPager.currentItem = 1
                }
            }
        })


        //選択した月が変わったら
        viewModel.selectedMonth.observe(viewLifecycleOwner) {
            view.weekCurrentMonth.text = "${viewModel.selectedMonth.value?.calendar?.get(Calendar.YEAR)}年 ${viewModel.selectedMonth.value?.calendar?.get(Calendar.MONTH)
                ?.plus(1)}月"
        }

        return view.root
    }
}