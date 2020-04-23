package com.example.isvirin.skbktestapp.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.isvirin.skbktestapp.R
import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.ui.NavigationHandler
import com.example.isvirin.skbktestapp.util.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.details_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance(bundle: Bundle): Fragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val snackBar by lazy {
        Snackbar.make(constraintLayout, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
    }

    private val viewModel: DetailsViewModel by viewModel()
    lateinit var contactId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        contactId = arguments!!.getString("id", "")
        if (savedInstanceState == null) {
            viewModel.get(contactId, refresh = false)
        }
        viewModel.contact.observe(viewLifecycleOwner, Observer { updateContact(it) })
        imageViewBack.setOnClickListener {
            val navigationHandler = activity as NavigationHandler
            navigationHandler.openContactLists()
        }
    }

    private fun updateContact(resource: Resource<Contact>?) {
        resource?.let {
            when (it.state) {
                ResourceState.LOADING -> swipeRefreshLayout.startRefreshing()
                ResourceState.SUCCESS -> swipeRefreshLayout.stopRefreshing()
                ResourceState.ERROR -> swipeRefreshLayout.stopRefreshing()
            }
            it.data?.let {
                textViewName.text = it.name
                textViewPhone.text = it.phone
                textViewTemperament.text = it.temperament.toString()
                textViewEducation.text =
                    handleDate(it.educationPeriod.start, context).plus(" - ").plus(handleDate(it.educationPeriod.end, context))
                textViewBiography.text = it.biography
            }
            it.message?.let { snackBar.show() }
        }
    }
}
