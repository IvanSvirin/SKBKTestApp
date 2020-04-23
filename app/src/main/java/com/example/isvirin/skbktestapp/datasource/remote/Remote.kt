package com.example.isvirin.skbktestapp.datasource.remote

import com.example.isvirin.skbktestapp.datasource.model.ContactEntity
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContactsApi {

    @GET("generated-01.json")
    fun getContactsSourceOne(): Single<List<ContactEntity>>

    @GET("generated-02.json")
    fun getContactsSourceTwo(): Single<List<ContactEntity>>

    @GET("generated-03.json")
    fun getContactsSourceThree(): Single<List<ContactEntity>>

}