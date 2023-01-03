package group4.sensimate.ui.components

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getPickedDateAsString(year: Int, month: Int, day: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    return dateFormat.format(calendar.time)
}