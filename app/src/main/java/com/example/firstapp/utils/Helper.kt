package com.example.firstapp.utils

import java.util.Calendar

class Helper {
    val _calender = Calendar.getInstance()

    fun GetTime() : String {
        return "${_calender.get(Calendar.HOUR_OF_DAY)}:${_calender.get(Calendar.MINUTE)}"
    }
}