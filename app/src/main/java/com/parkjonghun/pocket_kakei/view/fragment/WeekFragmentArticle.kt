package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.parkjonghun.pocket_kakei.databinding.FragmentWeekArticleBinding
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel

class WeekFragmentArticle : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentWeekArticleBinding.inflate(inflater, container, false)

        val viewModel: MainViewModel by activityViewModels()

        return view.root
    }
}