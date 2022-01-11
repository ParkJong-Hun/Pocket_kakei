package com.parkjonghun.pocket_kakei.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.parkjonghun.pocket_kakei.databinding.FragmentDayArticleBinding
import com.parkjonghun.pocket_kakei.view.recylcerview.DayOfMonth2Adapter
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

//FragmentDayから実際使うViewPagerの部分
class DayFragmentArticle: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentDayArticleBinding.inflate(inflater, container, false)

        val viewModel: MainViewModel by activityViewModels()

        val incomeAdapter = DayOfMonth2Adapter()
        val incomeLayoutManager = LinearLayoutManager(inflater.context)

        val expenditureAdapter = DayOfMonth2Adapter()
        val expenditureLayoutManager = LinearLayoutManager(inflater.context)

        view.dayIncomeList.layoutManager = incomeLayoutManager
        view.dayExpenditureList.layoutManager = expenditureLayoutManager
        view.dayIncomeList.adapter = incomeAdapter
        view.dayExpenditureList.adapter = expenditureAdapter


        //選択した日が変わったら
        viewModel.selectedDay.observe(viewLifecycleOwner) { it ->
            Log.d("", it.calendar.get(Calendar.DATE).toString() + expenditureLayoutManager.toString())
            CoroutineScope(Dispatchers.Main).launch {
                //選択した日の情報を利用し、データを加工
                val incomeSheetsOfSelectedDay = viewModel.optimizeForDay(true)
                //データがないわけじゃないと
                if (incomeSheetsOfSelectedDay != null) {
                    if (incomeSheetsOfSelectedDay.isNotEmpty()) {
                        //情報を表示
                        view.dayIncomeList.visibility = View.VISIBLE
                        view.dayIncomeListTitle.visibility = View.VISIBLE
                    } else {
                        //情報を非表示
                        view.dayIncomeList.visibility = View.GONE
                        view.dayIncomeListTitle.visibility = View.GONE
                    }
                    view.dayIncomeList.adapter = incomeAdapter
                    incomeAdapter.submitList(incomeSheetsOfSelectedDay)

                }
                val expenditureSheetsOfSelectedDay = viewModel.optimizeForDay(false)
                //データがないわけじゃないと
                if (expenditureSheetsOfSelectedDay != null) {
                    if (expenditureSheetsOfSelectedDay.isNotEmpty()) {
                        //情報を表示
                        view.dayExpenditureList.visibility = View.VISIBLE
                        view.dayExpenditureListTitle.visibility = View.VISIBLE
                    } else {
                        //情報を非表示
                        view.dayExpenditureList.visibility = View.GONE
                        view.dayExpenditureListTitle.visibility = View.GONE
                    }
                    view.dayExpenditureList.adapter = expenditureAdapter
                    expenditureAdapter.submitList(expenditureSheetsOfSelectedDay)
                }
                //データが全くないと
                if (expenditureSheetsOfSelectedDay == null && incomeSheetsOfSelectedDay == null) {
                    view.dayNoDataNotification.visibility = View.VISIBLE
                } else {
                    view.dayNoDataNotification.visibility = View.GONE
                }
            }
        }

        return view.root
    }
}