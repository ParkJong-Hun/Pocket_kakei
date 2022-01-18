package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
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

        val viewModel: MainViewModel by activityViewModels()

        val testData = listOf(Entry(0F, 0F), Entry(1F,0F), Entry(2F,1F), Entry(3F, 1F), Entry(4F, 0F), Entry(5F, 2F), Entry(6F, 2F))
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
        for (i in 0 until 6) {
            loadChart(charts[i], testData)
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
                initDetails()
                details[i].visibility = View.VISIBLE
                charts[i].animateY(300)
                viewModel.selectWeek(i + 1)
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
                        titles[i].text = "${weeks[i].first().get(Calendar.MONTH) + 1}月 " +
                                "${weeks[i].first().get(Calendar.DAY_OF_MONTH)}日 - " +
                                "${weeks[i].last().get(Calendar.MONTH) + 1}月 " +
                                "${weeks[i].last().get(Calendar.DAY_OF_MONTH)}日"
                    } else {
                        titles[i].text = "${weeks[i].first().get(Calendar.MONTH) + 1}月 " +
                                "${weeks[i].first().get(Calendar.DAY_OF_MONTH)}日"
                    }
                } else {
                    layouts[i].visibility = View.GONE
                }
            }
            //TODO: 格週の収入、支出を計算して表示
            //TODO: 日々の支出を計算してエントリーリストオブジェクトに返す
        }
        //
        fun updateDetailsUI() {
            viewModel.selectedWeek.value?.let {
                incomes[it - 1].text = "0 円"
                expenditures[it - 1].text = "0 円"
                incomes[it - 1].text = "${viewModel.getSelectedWeekIncomeMoney()} 円"
                expenditures[it - 1].text = "${viewModel.getSelectedWeekExpenditureMoney()} 円"
            }
        }
        //選択した月が変わったら
        viewModel.selectedMonth.observe(viewLifecycleOwner) {
            updateUI()
            updateDetailsUI()
        }
        //シートが変わったら
        viewModel.sheets.observe(viewLifecycleOwner) {
            updateUI()
            updateDetailsUI()
        }
        //
        viewModel.selectedWeek.observe(viewLifecycleOwner) {
            updateDetailsUI()
        }

        return view.root
    }
}