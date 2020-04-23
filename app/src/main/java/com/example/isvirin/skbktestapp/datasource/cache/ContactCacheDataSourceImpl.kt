package com.example.isvirin.skbktestapp.datasource.cache

import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.data.datasource.ContactCacheDataSource
import com.example.isvirin.skbktestapp.domain.model.Education
import com.example.isvirin.skbktestapp.domain.model.Temper
import io.reactivex.Single
import io.reactivex.functions.Function

class ContactCacheDataSourceImpl constructor(
    private val cache: ReactiveCache<List<Contact>>
) : ContactCacheDataSource {

    val key = "Contact List"

    override fun get(): Single<List<Contact>> =
        cache.load(key)

    override fun set(list: List<Contact>): Single<List<Contact>> =
        cache.save(key, list)

    override fun get(id: String): Single<Contact> =
        cache.load(key)
            .map { it.first() { it.id.equals(id) } }

    override fun set(item: Contact): Single<Contact> =
        cache.load(key)
            .map { it.filter { it.id != item.id }.plus(item) }
            .flatMap { set(it) }
            .map { item }
}