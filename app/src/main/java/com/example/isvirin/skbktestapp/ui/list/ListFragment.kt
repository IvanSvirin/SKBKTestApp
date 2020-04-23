package com.example.isvirin.skbktestapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.isvirin.skbktestapp.R
import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.ui.NavigationHandler
import com.example.isvirin.skbktestapp.util.Resource
import com.example.isvirin.skbktestapp.util.ResourceState
import com.example.isvirin.skbktestapp.util.startRefreshing
import com.example.isvirin.skbktestapp.util.stopRefreshing
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel
import kotlin.collections.ArrayList

class ListFragment : Fragment() {
    companion object {
        fun newInstance() =
            ListFragment()
    }
    private val viewModel: ListViewModel by viewModel()
    private val itemClick: (Contact) -> Unit =
        {
            val navigationHandler = activity as NavigationHandler
            navigationHandler.openContactDetails(it.id)
        }
    private val adapter = ContactListAdapter(itemClick)
    private val snackBar by lazy {
        Snackbar.make(swipeRefreshLayout, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { viewModel.get(refresh = true) }
    }
    private lateinit var contacts: ArrayList<Contact>
    private lateinit var contactsAfterSearch: ArrayList<Contact>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.get(refresh = false)
        }
        contactsRecyclerView.adapter = adapter
        viewModel.contacts.observe(viewLifecycleOwner, Observer { updateContacts(it) })
        swipeRefreshLayout.setOnRefreshListener { viewModel.get(refresh = true) }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                refreshListAfterSearch(p0!!)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                refreshListAfterSearch(p0!!)
                return true
            }
        })
        searchView.setOnSearchClickListener {  }
    }

    private fun updateContacts(resource: Resource<List<Contact>>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> swipeRefreshLayout.startRefreshing()
                ResourceState.SUCCESS -> swipeRefreshLayout.stopRefreshing()
                ResourceState.ERROR -> swipeRefreshLayout.stopRefreshing()
            }
            it.data?.let {
                contacts = ArrayList(it)
                adapter.submitList(contacts) }
            it.message?.let { snackBar.show() }
        }
    }

    fun refreshListAfterSearch(s: String) {
        contactsAfterSearch = ArrayList()
        for (i in 0..(contacts.size - 1)) {
            if (contacts[i].name.toUpperCase().contains(s.toUpperCase()) || contacts[i].phone.toUpperCase().contains(s.toUpperCase())) {
                contactsAfterSearch.add(contacts[i])
            }
        }
        adapter.submitList(contactsAfterSearch)
    }
}
