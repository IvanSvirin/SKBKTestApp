package com.example.isvirin.skbktestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.isvirin.skbktestapp.R
import com.example.isvirin.skbktestapp.domain.di.injectFeature
import com.example.isvirin.skbktestapp.ui.details.DetailsFragment
import com.example.isvirin.skbktestapp.ui.list.ListFragment

interface NavigationHandler {
    fun openContactLists()
    fun openContactDetails(id: String)
}

class MainActivity : AppCompatActivity(), NavigationHandler{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        injectFeature()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListFragment.newInstance())
                    .commitNow()
        }
    }

    override fun openContactLists() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment.newInstance())
            .commitNow()
    }

    override fun openContactDetails(id: String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance(bundle))
            .commitNow()    }
}
