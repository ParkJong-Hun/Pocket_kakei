package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AndroidViewModel
import androidx.viewpager2.widget.ViewPager2
import com.parkjonghun.pocket_kakei.databinding.FragmentDayBinding
import com.parkjonghun.pocket_kakei.view.viewpager.ViewPagerAdapter
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel

class DayFragment: Fragment() {
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
                    view.dayRestViewPager.currentItem = 1
                } else if(view.dayRestViewPager.currentItem == 2) {
                    view.dayRestViewPager.currentItem = 1
                }
            }
        })

        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}