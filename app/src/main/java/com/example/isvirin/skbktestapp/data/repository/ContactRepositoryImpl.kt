package com.example.isvirin.skbktestapp.data.repository

import android.util.Log
import com.example.isvirin.skbktestapp.data.SharedPreferencesStore
import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.data.datasource.ContactCacheDataSource
import com.example.isvirin.skbktestapp.data.datasource.ContactRemoteDataSource
import com.example.isvirin.skbktestapp.domain.repository.ContactRepository
import com.example.isvirin.skbktestapp.util.sharedPreferencesLastDataUpdate
import com.example.isvirin.skbktestapp.util.sharedPreferencesLastDataUpdateKey
import com.example.isvirin.skbktestapp.util.updatePeriod
import io.reactivex.Single

class ContactRepositoryImpl constructor(
    private val cacheDataSource: ContactCacheDataSource,
    private val remoteDataSource: ContactRemoteDataSource,
    private val sharedPreferencesStore: SharedPreferencesStore
) : ContactRepository {

    override fun get(refresh: Boolean): Single<List<Contact>> {
        when (refresh) {
            true -> {
                sharedPreferencesStore.writeLong(
                    sharedPreferencesLastDataUpdate,
                    sharedPreferencesLastDataUpdateKey, System.currentTimeMillis()
                )
                return remoteDataSource.get()
                    .flatMap { cacheDataSource.set(it) }
            }
            false -> {
                if ((System.currentTimeMillis() - sharedPreferencesStore.readLong(
                        sharedPreferencesLastDataUpdate,
                        sharedPreferencesLastDataUpdateKey
                    )!!) > updatePeriod
                ) {
                    return remoteDataSource.get()
                        .flatMap { cacheDataSource.set(it) }
                } else {
                    return cacheDataSource.get()
                        .onErrorResumeNext { get(true) }
                }
            }
        }
    }

    override fun get(id: String, refresh: Boolean): Single<Contact> =
        when (refresh) {
            true -> cacheDataSource.get(id)
                .onErrorResumeNext { get(id, true) }
            false -> cacheDataSource.get(id)
                .onErrorResumeNext { get(id, true) }
        }
}