package com.example.tdone

import android.util.DisplayMetrics
import com.example.tdone.createElements.CreateNoteActivity
import com.example.tdone.createElements.CreateTaskActivity
import com.example.tdone.viewIntoElements.TaskViewActivity

class getSize {
    fun getMainScreenWidth(activity: MainActivity): Int{
        val width = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(width)
        return width.widthPixels
    }
    fun getCreateNoteScreenWidth(activity: CreateNoteActivity): Int{
        val width = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(width)
        return width.widthPixels
    }

    fun getCreateTaskScreenWidth(activity: CreateTaskActivity): Int{
        val width = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(width)
        return width.widthPixels
    }

    fun getViewTaskScreenWidth(activity: TaskViewActivity): Int{
        val width = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(width)
        return width.widthPixels
    }
}