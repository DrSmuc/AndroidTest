package com.example.androidtest

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserListAdapter(context: Context, private val users: List<Pair<String, Int>>) :
    ArrayAdapter<Pair<String, Int>>(context, 0, users) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val nameTextView = view.findViewById<TextView>(R.id.itemNameTextView)
        val stepsTextView = view.findViewById<TextView>(R.id.itemStepsTextView)

        val user = users[position]
        nameTextView.text = user.first
        stepsTextView.text = user.second.toString()

        return view
    }
}

