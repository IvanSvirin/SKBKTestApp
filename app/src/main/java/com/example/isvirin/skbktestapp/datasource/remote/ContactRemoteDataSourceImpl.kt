package com.example.isvirin.skbktestapp.datasource.remote

import com.example.isvirin.skbktestapp.data.datasource.ContactRemoteDataSource
import com.example.isvirin.skbktestapp.datasource.model.ContactEntity
import com.example.isvirin.skbktestapp.datasource.model.mapToDomain
import com.example.isvirin.skbktestapp.domain.model.Contact
import io.reactivex.Single
import kotlin.collections.ArrayList

class ContactRemoteDataSourceImpl constructor(
    private val api: ContactsApi
) : ContactRemoteDataSource {

    override fun get(): Single<List<Contact>> {
        try {
            var result = ArrayList<ContactEntity>()
            api.getContactsSourceOne().apply {
                result.addAll(blockingGet())
                api.getContactsSourceTwo().apply {
                    result.addAll(blockingGet())
                    api.getContactsSourceThree().apply {
                        result.addAll(blockingGet())
                        return Single.just(result).map { it.mapToDomain() }
                    }
                }
            }
        } catch (e: Exception) {
            return api.getContactsSourceOne().map { it.mapToDomain() }
        }
    }
}