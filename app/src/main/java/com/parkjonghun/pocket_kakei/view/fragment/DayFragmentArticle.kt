package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.parkjonghun.pocket_kakei.databinding.FragmentDayArticleBinding
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel

//FragmentDayから実際使うViewPagerの部分
class DayFragmentArticle: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDayArticleBinding.inflate(inflater, container, false)

        val viewModel by activityViewModels<MainViewModel>()

        return view.root
    }
}