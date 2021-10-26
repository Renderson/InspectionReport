package com.example.inspectionreport.util

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun datePickerDialog(
    context: Context,
    datePickerOnDataSetListener: DatePickerDialog.OnDateSetListener,
    myCalendar: Calendar
) {
    DatePickerDialog(
        context,
        datePickerOnDataSetListener,
        myCalendar
            .get(Calendar.YEAR),
        myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)
    ).run {
        Date().time.also { datePicker.maxDate = it }
        show()
    }
}