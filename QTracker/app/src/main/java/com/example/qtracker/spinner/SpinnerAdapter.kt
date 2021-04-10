package com.example.qtracker.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.qtracker.R

class SpinnerAdapter(var ctx: Context, var title: List<String>): BaseAdapter() {

    override fun getView(i: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(ctx)
        val view1 = inflater.inflate(R.layout.support_simple_spinner_dropdown_item, null) as View
        val titles = view1.findViewById(R.id.title) as TextView
        titles.text = title[i]

        return view1
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return title.size
    }
}