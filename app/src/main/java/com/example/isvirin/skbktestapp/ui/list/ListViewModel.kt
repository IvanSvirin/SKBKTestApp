package com.example.isvirin.skbktestapp.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.domain.usecase.ContactsUseCase
import com.example.isvirin.skbktestapp.util.Resource
import com.example.isvirin.skbktestapp.util.setError
import com.example.isvirin.skbktestapp.util.setLoading
import com.example.isvirin.skbktestapp.util.setSuccess
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListViewModel constructor(private val contactsUseCase: ContactsUseCase) : ViewModel() {
    val contacts = MutableLiveData<Resource<List<Contact>>>()
    private val compositeDisposable = CompositeDisposable()

    fun get(refresh: Boolean = false) =
        compositeDisposable.add(contactsUseCase.get(refresh)
            .doOnSubscribe { contacts.setLoading() }
            .subscribeOn(Schedulers.io())
            .subscribe({ contacts.setSuccess(it) }, { contacts.setError(it.message) })
        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
