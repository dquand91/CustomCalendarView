package com.example.customcalendar.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.customcalendar.R
import com.example.customcalendar.viewholder.MyCalendarViewHolder
import java.util.*

class MyCalendarAdapter : RecyclerView.Adapter<MyCalendarViewHolder>() {

    lateinit var listDates: ArrayList<Date>
    lateinit var context: Context
    lateinit var showingDateCalendar: Calendar
    var selectedDate: Date? = null
    var listener: ListenerCellSelect? = null


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyCalendarViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_my_calendar_item, p0, false)
        return MyCalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listDates.size
    }

    override fun onBindViewHolder(viewHolder: MyCalendarViewHolder, position: Int) {
        viewHolder.myBindView(
            listDates[position],
            showingDateCalendar,
            selectedDate,
            listener
        )
    }


    interface ListenerCellSelect {

//        fun onExpSelect(expenditureDiff: ExpenditureDiff?)
        fun onDateSelect(selectDate: Date)

    }

}