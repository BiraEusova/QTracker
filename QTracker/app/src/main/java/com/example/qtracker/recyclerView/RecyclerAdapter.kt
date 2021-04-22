package com.example.qtracker.recyclerView

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qtracker.R
import kotlinx.android.synthetic.main.class_card.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var notes: MutableList<ContactsContract.CommonDataKinds.Note> = ArrayList()
    lateinit var context: Context
    lateinit var myView: View

    fun RecyclerAdapter(/*classes: MutableList<ContactsContract.CommonDataKinds.Note>,*/ context: Context){
        //this.notes = classes
        this.context = context
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notes.get(position)
        holder.bind(item, context, myView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.class_card, parent, false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id = view.idTextView
        val startTime = view.startTimeTextView
        val finishTime = view.finishTimeTextView
        val group = view.groupTextView
        var className = view.classNameTextView
        var teacher = view.teacherTextView

        fun bind(note: ContactsContract.CommonDataKinds.Note, context: Context, view: View){
            //id.text = note.id.toString()
            //title.text = note.title
            //subtitle.text = note.subtitle
            //valCheck = note.check
            //check.isChecked = valCheck
        }
    }

    fun getView(view: View){
        this.myView = view
    }
}