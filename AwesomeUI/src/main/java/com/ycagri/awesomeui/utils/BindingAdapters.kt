package com.ycagri.awesomeui.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("text_date")
    fun showDate(textView: TextView, timestamp: Long) {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        textView.text = sdf.format(Date(timestamp))
    }

    @JvmStatic
    @BindingAdapter("text_hour")
    fun showHour(textView: TextView, timestamp: Long) {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        textView.text = sdf.format(Date(timestamp))
    }
}