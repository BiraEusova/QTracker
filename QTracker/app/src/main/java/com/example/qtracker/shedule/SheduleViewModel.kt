package com.example.qtracker.shedule

import android.R.attr.password
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.qtracker.dataclasses.Lesson
import com.example.qtracker.recyclerView.RecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException
import java.net.Authenticator
import java.net.PasswordAuthentication


class SheduleViewModel(val context: Context) : ViewModel() {

    private lateinit var mDataBase: DatabaseReference
    private var LESSON_KEY: String = "Lesson"
    //private lateinit var document: Document

    private val url = "http://schedule.tsu.ru/students_schedule?faculty_id=1420&group_id=38211"
    val lessons = mutableListOf<Lesson>()
    //private lateinit var adapter: RecyclerAdapter

    fun getData(){

        mDataBase = FirebaseDatabase.getInstance().getReference(LESSON_KEY)
        //getParsedData()

       GlobalScope.async {
           getParsedData()
           //getSomeData()
        }


    }

    private fun getSomeData(){
        try {
            //val client = OkHttpClient()
            //val sslContext: SSLContext = SslUtils.getSslContextForCertificateFile(context, "BPClass2RootCA-sha2.cer")
            //client.setSslSocketFactory(sslContext.socketFactory)
            val document = Jsoup.connect(url).timeout(5000).validateTLSCertificates(false).get()
            //url.getSSLParameters().setEndpointIdentificationAlgorithm("HTTPS")
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun getParsedData(){
        try {
            val document = Jsoup.connect(url).timeout(10000).validateTLSCertificates(false).get()
            val weekday: Elements = document.select("div[class=weekday_line]")

            for(day: Int in 0 until weekday.size){

                val lessonsCell: Elements = weekday.select("div[class=lessons_cell]")
                for (cell: Int in 0 until lessonsCell.size){

                    val allLessonsAtThisTime: Elements = lessonsCell.select("div[class=all_lessons_info]")
                    val oneLesson: Elements = allLessonsAtThisTime.select("div[class=one_lesson_info]")
                    for (lesson: Int in 0 until oneLesson.size){
                        val name: String = oneLesson.select("label").text()
                        val group: String = oneLesson.select("div[class=groups]").text()
                        val style = oneLesson.select("div[class=type_employment]").toString()
                        var color: String = ""
                        color = when {
                            style.findAnyOf(strings = listOf("#ffa200"), startIndex = 0, ignoreCase = false) != null -> {
                                "Orange"
                            }
                            style.findAnyOf(strings = listOf("#2ec4e4"), startIndex = 0, ignoreCase = false) != null -> {
                                "Blue"
                            }
                            style.findAnyOf(strings = listOf("#ff0000"), startIndex = 0, ignoreCase = false) != null -> {
                                "Red"
                            }
                            else -> "DarkBlue"
                        }

                        GlobalScope.launch(Dispatchers.Main){
                            val id: String? = mDataBase.key
                            val newLesson = Lesson(id.toString(), name, "00:00", "00:00", "smb", group, color)
                            lessons.add(newLesson)
                            mDataBase.push().setValue(newLesson)
                        }
                    }
                }
            }
        }
        catch (e: IOException){
            e.printStackTrace()
        }
    }
}