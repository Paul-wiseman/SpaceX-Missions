package com.mindera.rocketscience.util

import com.mindera.rocketscience.core.R
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun getDateWithoutTime(dateString: String): UiText {
    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val dateTime = LocalDateTime.parse(dateString, formatter)
        return UiText.DynamicString(dateTime.toLocalDate().toString())
    } catch (e: Exception) {
        e.printStackTrace()
        UiText.StringResource(R.string.unable_to_get_time)
    }

}

fun getTimeFromDateString(dateString: String): UiText {
    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val dateTime = LocalDateTime.parse(dateString, formatter)
        return UiText.DynamicString(dateTime.toLocalTime().toString())
    } catch (e: Exception) {
        e.printStackTrace()
        UiText.StringResource(R.string.unable_to_get_time)
    }
}

fun daysFromToday(dateString: String): Long {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val date = OffsetDateTime.parse(dateString, formatter)
    val today = OffsetDateTime.now()
    return ChronoUnit.DAYS.between(date.toLocalDate(), today.toLocalDate())
}