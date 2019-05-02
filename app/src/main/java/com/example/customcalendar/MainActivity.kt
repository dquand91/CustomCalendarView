package com.example.customcalendar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.customcalendar.customView.MyCustomCalendarView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyCustomCalendarView.EventBetweenCalendarAndFragment {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myCalendarView.setEventHandler(this)
        myCalendarView.updateCalendar()

    }

    override fun onCalendarNextPressed() {
        // Xử lý khi nhấn nút Next trên Calendar
        myCalendarView.updateCalendar()
    }

    override fun onCalendarPreviousPressed() {
        // Xử lý khi nhấn nút Previous trên Calendar
        myCalendarView.updateCalendar()
    }
}
