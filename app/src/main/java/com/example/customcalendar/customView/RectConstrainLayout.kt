package com.example.customcalendar.customView

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class SquareConstrainLayout : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}