package com.grupo2.plusorder.utils

import java.time.LocalDate
import java.util.*
import java.time.Period

object dateUtils {
    // Calculate and return age of a date
    fun GetAge(date: Date) : Int{
        return Period.between(
            LocalDate.of(date.year, date.month, date.day),
            LocalDate.now()
        ).years;
    }
}