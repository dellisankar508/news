package com.developer.news.utils

import java.text.SimpleDateFormat
import java.util.Locale

private const val SERVER_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val DISPLAYABLE_DATE_PATTERN = "yyyy-MM-dd"

fun String.getDisplayableDate() =
    transformDateFormat(this, SERVER_DATE_PATTERN, DISPLAYABLE_DATE_PATTERN) ?: ""

fun transformDateFormat(date: String, fromPattern: String, toPattern: String): String? {
    return try {
        SimpleDateFormat(fromPattern, Locale.US)
            .parse(date)
            ?.let {
                SimpleDateFormat(toPattern, Locale.US).format(it)
            }
    } catch (ex: Exception) {
        null
    }
}