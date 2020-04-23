package com.example.isvirin.skbktestapp.data.datasource

import com.example.isvirin.skbktestapp.domain.model.Contact
import io.reactivex.Flowable
import io.reactivex.Single

interface ContactCacheDataSource {

    fun get(): Single<List<Contact>>

    fun set(item: Contact): Single<Contact>

    fun get(id: String): Single<Contact>

    fun set(list: List<Contact>): Single<List<Contact>>
}

interface ContactRemoteDataSource {

    fun get(): Single<List<Contact>>

}