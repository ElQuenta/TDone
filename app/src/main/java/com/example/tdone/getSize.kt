package com.example.tdone

import android.app.Activity
import android.util.DisplayMetrics

class getSize {
    fun getScreenWidth(activity: MainActivity): Int{
        val width = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(width)
        return width.widthPixels
    }
}