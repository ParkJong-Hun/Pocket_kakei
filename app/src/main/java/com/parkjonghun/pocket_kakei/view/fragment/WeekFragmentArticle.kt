package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.FragmentWeekArticleBinding
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import java.util.*

@SuppressLint("SetTextI18n")
class WeekFragmentArticle : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentWeekArticleBinding.inflate(inflater, container, false)



        val layouts: List<ConstraintLayout> = listOf(view.firstWeekLayout, view.secondWeekLayout, view.thirdWeekLayout, view.fourthWeekLayout, view.fifthWeekLayout, view.sixthWeekLayout)
        val titles: List<TextView> = listOf(view.firstWeekTitle, view.secondWeekTitle, view.thirdWeekTitle, view.fourthWeekTitle, view.fifthWeekTitle, view.sixthWeekTitle)
        val details: List<ConstraintLayout> = listOf(view.firstWeekDetails, view.secondWeekDetails, view.thirdWeekDetails, view.fourthWeekDetails, view.fifthWeekDetails, view.sixthWeekDetails)
        val charts: List<LineChart> = listOf(view.firstWeekChart, view.secondWeekChart, view.thirdWeekChart, view.fourthWeekChart, view.fifthWeekChart, view.sixthWeekChart)
        val incomes: List<TextView> = listOf(view.firstWeekIncomeValue, view.secondWeekIncomeValue, view.thirdWeekIncomeValue, view.fourthWeekIncomeValue, view.fifthWeekIncomeValue, view.sixthWeekIncomeValue)
        val expenditures: List<TextView> = listOf(view.firstWeekExpenditureValue, view.secondWeekExpenditureValue, view.thirdWeekExpenditureValue, view.fourthWeekExpenditureValue, view.fifthWeekExpenditureValue, view.sixthWeekExpenditureValue)
        val bigExpenditures: List<TextView> = listOf(view.firstWeekExpenditure, view.secondWeekExpenditure, view.thirdWeekExpenditure, view.fourthWeekExpenditure, view.fifthWeekExpenditure, view.sixthWeekExpenditure)



        val viewModel: MainViewModel by activityViewModels()


        //アプリスタイルのグラフを表示
        fun loadChart(chart: LineChart, entry: List<Entry>) {
            val dataSet = LineDataSet(entry, "dataSet")
            val lineData = LineData(dataSet)

            dataSet.apply {
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawFilled(true)
                setDrawCircleHole(false)
                color = Color.GREEN
                setCircleColor(Color.GREEN)
                fillColor = Color.GREEN
                valueTextSize = 14F
                valueTextColor = Color.GREEN
            }
            chart.apply {
                data = lineData
                xAxis.isEnabled = false
                axisLeft.isEnabled = false
                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
            }
        }


        //詳細情報を全部見えないように
        fun initDetails() {
            for(i in 0 until 6) {
                details[i].visibility = View.GONE
            }
        }
        //格週をクリックしたら
        for(i in 0 until 6) {
            layouts[i].setOnClickListener{
                var check = false
                if(details[i].visibility == View.VISIBLE){
                    check = true
                }
                initDetails()
                if (check) {
                    viewModel.selectWeek(null)
                } else {
                    details[i].visibility = View.VISIBLE
                    viewModel.selectWeek(i + 1)
                    charts[i].animateY(300)
                }
            }
        }


        //UIアップデート
        fun updateUI() {
            //格週が何日から何日までか計算して表示
            val weeks: MutableList<List<Calendar>> = mutableListOf()
            for (i in 0..5) {
                layouts[i].visibility = View.VISIBLE
            }
            for (i in 1..6) {
                val week = viewModel.getWeekOnMonth(i)
                if (week.isNotEmpty()) {
                    weeks.add(week)
                } else {
                    weeks.add(emptyList())
                }
            }
            for (i in 0 until 6) {
                if (weeks[i].isNotEmpty()) {
                    if (weeks[i].size != 1) {
                        titles[i].text = "${weeks[i].first().get(Calendar.MONTH) + 1}${resources.getString(R.string.month)} " +
                                "${weeks[i].first().get(Calendar.DAY_OF_MONTH)}${resources.getString(R.string.day)} - " +
                                "${weeks[i].last().get(Calendar.MONTH) + 1}${resources.getString(R.string.month)} " +
                                "${weeks[i].last().get(Calendar.DAY_OF_MONTH)}${resources.getString(R.string.day)}"
                    } else {
                        titles[i].text = "${weeks[i].first().get(Calendar.MONTH) + 1}${resources.getString(R.string.month)} " +
                                "${weeks[i].first().get(Calendar.DAY_OF_MONTH)}${resources.getString(R.string.day)}"
                    }
                    bigExpenditures[i].text = "${viewModel.getWeekExpenditureMoney(weeks[i])}${resources.getString(R.string.currency)}"
                } else {
                    layouts[i].visibility = View.GONE
                }
            }
        }
        //詳細情報を表示
        fun updateDetailsUI() {
            viewModel.selectedWeek.value?.let {
                incomes[it - 1].text = "0 " + resources.getString(R.string.currency)
                expenditures[it - 1].text = "0 " + resources.getString(R.string.currency)
                incomes[it - 1].text = "${viewModel.getSelectedWeekIncomeMoney()} " + resources.getString(R.string.currency)
                expenditures[it - 1].text = "${viewModel.getSelectedWeekExpenditureMoney()} " + resources.getString(R.string.currency)
            }
        }
        //選択した月が変わったら
        viewModel.selectedMonth.observe(viewLifecycleOwner) {
            updateUI()
        }
        //シートが変わったら
        viewModel.sheets.observe(viewLifecycleOwner) {
            updateUI()
        }
        //選択した週が変わったら
        viewModel.selectedWeek.observe(viewLifecycleOwner) {
            if(it != null) {
                loadChart(charts[it - 1], viewModel.loadSelectedWeekChartData())
            }
            updateDetailsUI()
        }

        return view.root
    }
}