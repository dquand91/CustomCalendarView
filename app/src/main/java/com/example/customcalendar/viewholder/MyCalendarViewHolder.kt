package com.example.customcalendar.viewholder

import android.graphics.Typeface
import android.graphics.Typeface.BOLD
import android.support.constraint.ConstraintLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.customcalendar.R
import com.example.customcalendar.adapter.MyCalendarAdapter
import java.util.*

class MyCalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvDate = view.findViewById<TextView>(R.id.tvDate)
    private val cellDateLayout = view.findViewById<ConstraintLayout>(R.id.cellDateLayout)
    private val context = view.context

    /**
     *  Method này được gọi mỗi khi adapter vẽ 1 item trong list ngày tháng
     * @param currentDateInput : truyền vào ngày đang được vẽ (item đang được )
     * @param showingDate : truyền vào ngày của trang lịch đang hiển thị
     * @param dateSelected : truyền vào ngày đang được chọn
     * @param listener : đăng ký callback để trả về ngày nào được chọn
     */
    fun myBindView(currentDateInput : Date,
                   showingDate : Calendar,
                   dateSelected : Date?,
                   listener: MyCalendarAdapter.ListenerCellSelect? = null
    ){
        // to reset View when draw a new item
        resetViewDefault()

        // Lấy ra ngày tháng của trang lịch đang hiển thị.doc
        // Cái này không phải ngày tháng năm hiện tại
        val showingMonth = showingDate.get(Calendar.MONTH) + 1
        val showingYear = showingDate.get(Calendar.YEAR)

        // Lấy ra ngày hiện tại dựa vào ngày đang được
        val currentDateTime = Calendar.getInstance()
        currentDateTime.time = currentDateInput

        // Lấy ra ngày tháng năm hiện tại đang được vẽ
        val currentDay = currentDateTime.get(Calendar.DATE)
        val currentMonth = currentDateTime.get(Calendar.MONTH) + 1
        val currentYear = currentDateTime.get(Calendar.YEAR)

        // Lấy ra ngày mà user đã chọn
        var selectedDay = -1
        if(dateSelected != null){
            val selectedDateTime = getCalendarFromTimestamp(dateSelected.time)
            selectedDay = selectedDateTime.get(Calendar.DATE)
        }

        if(currentMonth != showingMonth || currentYear != showingYear){
            // Nếu tháng hiện tại khác tháng đang hiển thị hoặc năm hiện tại khác năm đang hiển thị
            // Xử lý UI các ngày không phải của tháng đang hiển thị
            // (ví dụ ngày 30, 31 của tháng trước)
            // Ví dụ ngày 1,2,3 của tháng tiếp theo
            tvDate.setTextColor(ResourcesCompat.getColor(context.resources, R.color.calendar_date_disable, null))
            cellDateLayout.setBackgroundColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.calendar_background_item_disable,
                    null
                )
            )
        } else if(selectedDay == currentDay){
            // Xử lý UI của ngày đang được chọn (highlight)
            tvDate.setTypeface(null, BOLD)
            cellDateLayout.setBackgroundColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.calendar_highlight,
                    null
                )
            )
        } else {
            // Xử lý UI các ngày của tháng đang hiển thị
            tvDate.setTextColor(ResourcesCompat.getColor(context.resources, R.color.black, null))
            cellDateLayout.setBackgroundColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.calendar_background_item_enable,
                    null
                )
            )
            // Chỉ gắn sự kiện click cho những item của tháng hiện tại
            // Các item của tháng trước đó sẽ ko có sự kiện click
            itemView.setOnClickListener{
                listener?.let { eventHandler ->
                    eventHandler.onDateSelect(currentDateInput)
                }
            }
        }

        tvDate.text = currentDay.toString()
    }


    private fun resetViewDefault() {
        itemView.setOnClickListener(null)
        tvDate.setTypeface(null, Typeface.NORMAL)
        tvDate.setTextColor(ResourcesCompat.getColor(context.resources, R.color.white, null))
    }


    /**
     * Create Calender from timestamp
     */
    fun getCalendarFromTimestamp(timestamp: Long): Calendar {
        val date = Date(timestamp)
        return Calendar.getInstance().apply { time = date }
    }


}