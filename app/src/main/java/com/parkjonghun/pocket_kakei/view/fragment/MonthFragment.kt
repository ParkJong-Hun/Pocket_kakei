package com.parkjonghun.pocket_kakei.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.parkjonghun.pocket_kakei.R
import com.parkjonghun.pocket_kakei.databinding.FragmentMonthBinding
import com.parkjonghun.pocket_kakei.model.Sheet
import com.parkjonghun.pocket_kakei.view.activity.AddActivity
import com.parkjonghun.pocket_kakei.view.decorator.BackgroundDecorator
import com.parkjonghun.pocket_kakei.view.decorator.SaturdayDecorator
import com.parkjonghun.pocket_kakei.view.decorator.SundayDecorator
import com.parkjonghun.pocket_kakei.view.recylcerview.DayOfMonthAdapter
import com.parkjonghun.pocket_kakei.viewmodel.MainViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MonthFragment: Fragment() {
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentMonthBinding.inflate(inflater, container, false)

        //RecyclerView設定
        val adapter = DayOfMonthAdapter()
        val layoutManager = LinearLayoutManager(inflater.context)
        view.monthRecyclerView.layoutManager = layoutManager

        val viewModel: MainViewModel by activityViewModels()
        //上段のUI更新
        fun updateTopUI() {
            //収入
            view.monthIncomeValue.text = "${viewModel.getAllIncomeMoney()}円"
            //支出
            view.monthExpenditureValue.text = "${viewModel.getAllExpenditureMoney()}円"
            //現金
            view.monthExpenditureCashValue.text = "${viewModel.getCashExpenditureMoney()}円"
            //カード
            view.monthExpenditureCardValue.text = "${viewModel.getCardExpenditureMoney()}円"
            //残高
            val balance = viewModel.getAllIncomeMoney() - viewModel.getAllExpenditureMoney()
            view.monthBalanceValue.text = "${balance}円"
            if(balance < 0) {
                view.monthBalanceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            } else {
                view.monthBalanceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
        }
        //Sheetsが変わったら
        viewModel.sheets.observe(viewLifecycleOwner) {
            //支出、収入日の背景
            val addedDay: List<Sheet>? = viewModel.sheets.value?.filter { it.isAdd }
            val paidDay: List<Sheet>? = viewModel.sheets.value?.filter { !it.isAdd }

            val addedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_add_money, null)
            val paidMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_pay_money, null)
            val usedMoneyIcon: Drawable = view.root.resources.getDrawable(R.drawable.ic_use_money, null)

            view.monthCalendar.addDecorators(
                addedDay?.let { it1 -> BackgroundDecorator(addedMoneyIcon, it1) },
                paidDay?.let { it1 -> BackgroundDecorator(paidMoneyIcon, it1) },
            )
            //データが支出と収入どっちもあったら
            if(addedDay != null && paidDay != null) {
                val union = addedDay + paidDay
                val intersection = union.groupBy { it.date.time }.filter { it.value.size > 1 }.flatMap { it.value }.distinct()
                view.monthCalendar.addDecorator(BackgroundDecorator(usedMoneyIcon, intersection))
            }

            updateTopUI()
        }
        //選択した月が変わったら
        viewModel.selectedMonth.observe(viewLifecycleOwner) {
            updateTopUI()
        }
        //選択した日が変わったら
        viewModel.selectedDay.observe(viewLifecycleOwner) {
            CoroutineScope(Dispatchers.Main).launch {
                //選択した日の情報を利用し、データを加工
                val sheetsOfSelectedDay = viewModel.optimizeForMonth()
                //データがないわけじゃないと
                if (sheetsOfSelectedDay != null) {
                    if (sheetsOfSelectedDay.isNotEmpty()) {
                        //情報を表示
                        view.monthRecyclerView.visibility = View.VISIBLE
                        view.noDataNotification.visibility = View.GONE
                        view.monthRecyclerView.adapter = adapter
                        adapter.submitList(sheetsOfSelectedDay)
                    } else {
                        ////情報を非表示
                        view.monthRecyclerView.visibility = View.GONE
                        view.noDataNotification.visibility = View.VISIBLE
                        view.monthRecyclerView.adapter = adapter
                        adapter.submitList(sheetsOfSelectedDay)
                    }
                }
                Log.d("", sheetsOfSelectedDay.toString())
            }
        }

        //AddActivityからデータを追加したら
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == AppCompatActivity.RESULT_OK) {
                //データ更新
                viewModel.loadSheets()
            }
        }


        //FloatingButtonクリックしたら
        view.addButton.setOnClickListener {
            Intent(activity, AddActivity::class.java).apply {
                putExtra("calendar", viewModel.calendarDayToString(view.monthCalendar.selectedDate))
                activityResult.launch(this)
            }
        }
        //今日を選択する
        view.monthCalendar.selectedDate = CalendarDay.today()
        //週末の色
        view.monthCalendar.addDecorators(
            SaturdayDecorator(),
            SundayDecorator(),
        )
        //日を選択したら
        view.monthCalendar.setOnDateChangedListener { _, date, selected ->
            if (selected) {
                //データ更新
                viewModel.selectDay(date)
            }
        }
        //月を選択したら
        view.monthCalendar.setOnMonthChangedListener { _, date ->
            //データ更新
            viewModel.selectMonth(date)
        }






        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}