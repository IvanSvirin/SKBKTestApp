package com.example.isvirin.skbktestapp.datasource.model

import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.domain.model.Education
import com.example.isvirin.skbktestapp.domain.model.Temper
import com.squareup.moshi.Json

data class ContactEntity(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "phone") val phone: String?,
    @field:Json(name = "height") val height: Float?,
    @field:Json(name = "biography") val biography: String?,
    @field:Json(name = "temperament") val temperament: Temper?,
    @field:Json(name = "educationPeriod") val educationPeriod: Education?
)

fun ContactEntity.mapToDomain(): Contact =
    Contact(id, name?:"", phone?:"", height?:0f, biography?:"", temperament?: Temper.melancholic,
        educationPeriod?: Education("", "")
    )

fun List<ContactEntity>.mapToDomain(): List<Contact> = map { it.mapToDomain() }

fun Contact.mapToData(): ContactEntity =
    ContactEntity(id, name?:"", phone?:"", height?:0f, biography?:"", temperament?: Temper.melancholic,
        educationPeriod?:Education("", ""))
