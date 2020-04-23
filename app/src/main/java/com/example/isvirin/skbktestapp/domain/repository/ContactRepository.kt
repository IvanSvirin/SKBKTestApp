package com.example.isvirin.skbktestapp.domain.repository

import com.example.isvirin.skbktestapp.domain.model.Contact
import io.reactivex.Flowable
import io.reactivex.Single

interface ContactRepository {
    fun get(refresh: Boolean): Single<List<Contact>>

    fun get(id: String, refresh: Boolean): Single<Contact>
}