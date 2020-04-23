package com.example.isvirin.skbktestapp.util

import android.content.Context
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun handleDate(inStr: String?, context: Context?): String? {
    return if (inStr != null && inStr.length > 0) {
        val date: Date =
            formatIn.parse(inStr!!.replace("Z$".toRegex(), "+0000"))!!
        formatOutShort.format(date)
    } else {
        ""
    }
}
