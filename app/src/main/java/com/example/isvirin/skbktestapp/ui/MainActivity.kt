package com.example.isvirin.skbktestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.isvirin.skbktestapp.R
import com.example.isvirin.skbktestapp.domain.di.injectFeature
import com.example.isvirin.skbktestapp.ui.details.DetailsFragment
import com.example.isvirin.skbktestapp.ui.list.ListFragment

interface NavigationHandler {
    fun openContactList()
    fun openContactDetails(id: String)
}
const val LIST_FRAGMENT = "LIST_FRAGMENT"
const val DETAILS_FRAGMENT = "DETAILS_FRAGMENT"

class MainActivity : AppCompatActivity(), NavigationHandler {
    lateinit var currentFragment: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        injectFeature()

        if (savedInstanceState == null) {
            currentFragment = LIST_FRAGMENT
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance())
                .commitNow()
        }
    }

    override fun openContactList() {
        currentFragment = LIST_FRAGMENT
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment.newInstance())
            .commitNow()
    }

    override fun openContactDetails(id: String) {
        currentFragment = DETAILS_FRAGMENT
        val bundle = Bundle()
        bundle.putString("id", id)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance(bundle))
            .commitNow()
    }

    override fun onBackPressed() {
        when(currentFragment) {
            LIST_FRAGMENT -> super.onBackPressed()
            DETAILS_FRAGMENT -> openContactList()
            else  -> super.onBackPressed()
        }
    }
}
