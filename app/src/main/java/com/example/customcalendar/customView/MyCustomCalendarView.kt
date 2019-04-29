package com.example.customcalendar.customView

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.customcalendar.R
import kotlinx.android.synthetic.main.view_my_calendar.view.*
import java.text.SimpleDateFormat
import java.util.*

class MyCustomCalendarView : ConstraintLayout {

    companion object {
        // Days of 5 Weeks
        // Số ngày tối đa có thể hiển thị trong 1 trang
        // Hoặc nói cách khác là: có 35 item (ngày) trong 1 trang hiển thị
        const val MAX_DAY_COUNT = 35

        // Num of Week
        // Số cột của 1 trang
        const val NUM_DAY_OF_WEEK = 7
    }

    public val DEFAULT_DATE_FORMAT = "yyyyMM"

    private var dateFormat = DEFAULT_DATE_FORMAT

    private lateinit var btnPrev: ImageView
    private lateinit var btnNext: ImageView
    private lateinit var txtDate: TextView
    private lateinit var grid: RecyclerView
    private lateinit var currentDate: Calendar
    private var eventHandler: EventBetweenCalendarAndFragment? = null
    private var todayMonth: Int = -1
    private var todayYear: Int = -1


    constructor(context: Context?) : super(context) {
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }


    private fun initView(context: Context?, attrs: AttributeSet? = null){
        currentDate = Calendar.getInstance().apply {
            // Tháng hiện tại
            todayMonth = this.get(Calendar.MONTH)
            // Năm hiện tại
            todayYear = this.get(Calendar.YEAR)
        }

        context?.let {
            val inflater = it.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.view_my_calendar, this, true)
            btnPrev = findViewById(R.id.ivPrev)
            btnNext = findViewById(R.id.ivNext)
            txtDate = findViewById(R.id.tvDate)
            grid = findViewById(R.id.gridCalendar)

            btnNext.setOnClickListener {
                // Cộng thêm 1 tháng để hiển thị khi bấm nút NEXT
                currentDate.add(Calendar.MONTH, 1)

                // Báo hiệu cho activity/fragment biết đã bấm nút Next
                eventHandler?.onCalendarNextPressed()

                checkStateNextButton()
            }

            btnPrev.setOnClickListener{
                // Tương tự nút NEXT
                currentDate.add(Calendar.MONTH, -1)
                eventHandler?.onCalendarPreviousPressed()
                checkStateNextButton()
            }

            // tắt chức năng scroll của RecyclerView (ở đây là grid) đi
            ViewCompat.setNestedScrollingEnabled(grid, false)

            grid.apply {
                // định nghĩa gridlayout cho recycler với 7 cột
                this.layoutManager = GridLayoutManager(context, NUM_DAY_OF_WEEK)

                // Để thêm viền vào ngăn cách từng item trong gridView
                addItemDecoration(SpaceItemDecoration(resources.getDimension(R.dimen._1sdp).toInt(), true))
            }



        }
    }


    // Gọi hàm này sau khi lấy dữ liệu từ API về xong update lại Calendar
    fun updateCalendar(){
        val mCellList = ArrayList<Date>()
        val mCalendar = currentDate.clone() as Calendar

        // determine the cell for current month's beginning
        // Xác định item (cell) cho tháng bắt đầu hiện tại
        mCalendar.set(Calendar.DAY_OF_MONTH, 1)
        val monthBeginningCell = mCalendar.get(Calendar.DAY_OF_WEEK) - 1


        // move calendar backwards to the beginning of the week
        mCalendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

        // fill cells
        while (mCellList.size < MAX_DAY_COUNT) {
            mCellList.add(mCalendar.time)
            mCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // TODO: Handle push data to adapter of recyclerView

    }

    private fun checkStateNextButton() {
        // Để check xem có nên hiển thị nút NEXT hay không.
        // Điều kiện hiển thị nút NEXT:
        //     Nếu trang calendar đang hiển thị là tháng hiện tại (now, the present) => ẩn nút Next
        //      Còn lại thì hiển thị nút NEXT
        val currentMonth = currentDate.get(Calendar.MONTH)
        val currentYear = currentDate.get(Calendar.YEAR)
        if (currentMonth == todayMonth && currentYear == todayYear) {
            btnNext.visibility = View.INVISIBLE
        } else {
            btnNext.visibility = View.VISIBLE
        }
    }



    fun setEventHandler(eventHandler: EventBetweenCalendarAndFragment) {
        this.eventHandler = eventHandler
    }

    /**
     * Set Current time Header
     */
    private fun setHeader(calendarDate: Calendar) {
        // update title
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        txtDate.text = sdf.format(calendarDate.time)
    }

    /**
     * Giao tiếp giữa CalendarView và Activity/Fragment
     */
    interface EventBetweenCalendarAndFragment {

        fun onCalendarPreviousPressed()

        fun onCalendarNextPressed()

        // Handle khi 1 item trong Calendar được click
//        fun onExpDiffSelect(item: ExpenditureDiff)

    }

}