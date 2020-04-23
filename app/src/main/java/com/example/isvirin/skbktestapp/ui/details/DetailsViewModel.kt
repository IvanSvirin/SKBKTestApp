package com.example.isvirin.skbktestapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.domain.usecase.ContactUseCase
import com.example.isvirin.skbktestapp.util.Resource
import com.example.isvirin.skbktestapp.util.setError
import com.example.isvirin.skbktestapp.util.setLoading
import com.example.isvirin.skbktestapp.util.setSuccess
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel constructor(private val contactUseCase: ContactUseCase) : ViewModel() {
    val contact = MutableLiveData<Resource<Contact>>()
    private val compositeDisposable = CompositeDisposable()

    fun get(id: String, refresh: Boolean = false) =
        compositeDisposable.add(contactUseCase.get(id, refresh)
            .doOnSubscribe { contact.setLoading() }
            .subscribeOn(Schedulers.io())
            .subscribe(
                { contact.setSuccess(it) },
                { contact.setError(it.message) })
        )

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
