package com.example.vitesseapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(birthday: Long): String {
    val birthday = Date(birthday).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = birthday.format(formatter)
    val year = Calendar.getInstance().get(Calendar.YEAR)
    val age = year - birthday.year
    return "$formattedDate ($age years)"
}