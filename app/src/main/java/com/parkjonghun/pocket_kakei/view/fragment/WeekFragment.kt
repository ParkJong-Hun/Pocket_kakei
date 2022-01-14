package com.parkjonghun.pocket_kakei.view.fragment

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

class WeekFragment: Fragment() {
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
        view.weekViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(view.weekViewPager.currentItem == 0) {
                    view.weekViewPager.currentItem = 1
                } else if(view.weekViewPager.currentItem == 2) {
                    view.weekViewPager.currentItem = 1
                }
            }
        })

        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}