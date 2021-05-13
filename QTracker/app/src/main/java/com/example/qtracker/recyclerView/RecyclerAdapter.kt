package com.example.qtracker.recyclerView

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.qtracker.R
import com.example.qtracker.dataclasses.Lesson
import kotlinx.android.synthetic.main.class_card.view.*

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var lessons: MutableList<Lesson> = ArrayList()
    lateinit var context: Context
    lateinit var myView: View

    /*fun RecyclerAdapter(lessons: MutableList<Lesson>, context: Context){
        this.lessons = lessons
        this.context = context
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.class_card, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = lessons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lessons.get(position)
        holder.bind(item, context, myView)
    }

    /*fun getView(view: View){
        this.myView = view
    }*/

    fun set(list: MutableList<Lesson>, context: Context, view:View){
        //this.lessons.clear()
        this.lessons.addAll(list)
        this.context = context
        this.myView = view
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id = view.idTextView
        val startTime = view.startTimeTextView
        val finishTime = view.finishTimeTextView
        val group = view.groupTextView
        var name = view.classNameTextView
        var teacher = view.teacherTextView
        var color: String = ""

        fun bind(lesson: Lesson, context: Context, view: View){
            id.text = lesson.id
            startTime.text = lesson.startTime
            finishTime.text = lesson.finishTime
            name.text = lesson.name
            group.text = lesson.group
            teacher.text = lesson.teacher
            val v = this.itemView
            v.setBackgroundColor(getColor(color))
        }

        fun getColor(color: String): Int {
            return when(color){
                "Red" -> R.color.lection
                "Blue" -> R.color.laboratory
                "Orange" -> R.color.seminar
                else -> R.color.practice
            }
        }
    }



}