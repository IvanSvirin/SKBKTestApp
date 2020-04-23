package com.example.isvirin.skbktestapp.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

val formatIn: DateFormat =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)
val formatOutShort: DateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)


const val sharedPreferencesLastDataUpdate = "LAST_DATA_UPDATE"
const val sharedPreferencesLastDataUpdateKey = "LAST_DATA_UPDATE_KEY"
const val updatePeriod = 60 * 1000L
