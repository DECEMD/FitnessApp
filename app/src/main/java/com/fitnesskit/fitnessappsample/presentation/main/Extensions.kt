package com.fitnesskit.fitnessappsample.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.fitnesskit.domain.modules.Lessons
import com.fitnesskit.fitnessappsample.presentation.fragments.navigation.Navigator
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

fun Context.makeToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

fun Context.calculateDiff(startTime: String, endTime: String){
    val df: DateFormat = SimpleDateFormat("hh:mm")
    val date1 = df.parse(startTime)
    val date2 = df.parse(endTime)
    if (date1 != null && date2 != null){
        val diff = date2.time - date1.time
        val hms = String.format("%01dч. %02dмин.", TimeUnit.MILLISECONDS.toHours(diff),
            TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(
                diff)))
        if (hms.contains("0ч.")){
            Log.d("log", hms.drop(4))
        } else if (hms.contains("00мин.")){
            Log.d("log", hms.dropLast(7))
        } else {
            Log.d("log", hms)
        }
    }
}

fun MenuItem.avoidMultipleClicks() {
    this.isEnabled = false
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        this.isEnabled = true
    }, 1000)
}

@SuppressLint("NewApi")
fun String.formatDate() : String {
    val list = this.split('-').map { it.toInt() }
    println(list[2])
    val mm = DateFormatSymbols().months[list[1]-1]
    val dayOfTheWeek = LocalDate.parse(
        this , DateTimeFormatter.ofPattern( "uuuu-MM-dd" ))
        .dayOfWeek
        .getDisplayName(
            TextStyle.FULL , Locale("ru","RU"))

    return if (list[2] < 10){
        StringBuilder().append("${dayOfTheWeek}, ").append("0${list[2]} ").append(mm).toString()
    } else {
        StringBuilder().append("${dayOfTheWeek}, ").append("${list[2]} ").append(mm).toString()
    }
}

