package com.example.isvirin.skbktestapp.domain.usecase

import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.domain.repository.ContactRepository
import io.reactivex.Single

class ContactsUseCase constructor(
    private val contactRepository: ContactRepository
) {

    fun get(refresh: Boolean): Single<List<Contact>> =
        contactRepository.get(refresh)
}

class ContactUseCase constructor(
    private val contactRepository: ContactRepository
) {

    fun get(id: String, refresh: Boolean): Single<Contact> =
        contactRepository.get(id, refresh)
}
