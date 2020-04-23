package com.example.isvirin.skbktestapp.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.isvirin.skbktestapp.R
import com.example.isvirin.skbktestapp.domain.model.Contact
import com.example.isvirin.skbktestapp.util.inflate
import kotlinx.android.synthetic.main.item_contact.view.*


class ContactListAdapter constructor(private val itemClick: (Contact) -> Unit) :
    ListAdapter<Contact, ContactListAdapter.ViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_contact)) {

        fun bind(item: Contact) {
            itemView.textViewName.text = item.name
            itemView.textViewPhone.text = item.phone
            itemView.textViewHeight.text = item.height.toString()
            itemView.setOnClickListener { itemClick.invoke(item) }
        }
    }
}
private class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
        oldItem == newItem
}