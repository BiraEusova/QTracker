package com.example.qtracker

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.*


@Suppress("DEPRECATION")
class test : AppCompatActivity() {
    var offline = false
    var request: String? = null
    var WeekNumber: String? = null
    var count = 0

    //Виджеты
    var weeknumber: TextView? = null
    var timetable: EditText? = null
    var next: Button? = null
    var down: Button? = null
    fun formating(day: String?) {
        var DayTimetable = ""
        val weeks = request!!.split("newweek".toRegex()).toTypedArray()
        var DayData: String? = "" //Тут будет день недели и дата
        /*
        Переменные ниже будут содержать информацию о каждой паре
        Всего в день может быть семь пар
        массив less содержит названия каждого предмета
        массив tich содержит ФИО преподавателя каждого предмета
        массив aud содержит аудиторию, в которой будет проходить предмет (например Д-230)
         */
        val less = arrayOfNulls<String>(7)
        val tich = arrayOfNulls<String>(7)
        val aud = arrayOfNulls<String>(7)
        for (thisweek in weeks) { //пробегаемся по неделям
            if (thisweek.indexOf(day!!) != -1) { //Если нужный нам день найден в этой неделе то...
                WeekNumber = thisweek.split(" ".toRegex()).toTypedArray()[0] //Достаём номер недели
                for (thisday in thisweek.split("newday".toRegex())
                    .toTypedArray()) { //Теперь пробегаемся по дням этой недели
                    if (thisday.indexOf(day) != -1) { //Если данный день совпадает с нужным нам днём, то...
                        //Делаем так, тобы перед каждой парой была приставка newless
                        //пара всегда начинается с соответствующей приставки пр. лек. лаб. и пр.
                        var newthisday = thisday.replace("no", "newless")
                            .replace("пр.", "newlessпр.")
                            .replace("лек.", "newlessлек.")
                            .replace("лаб.", "newlessлаб.")

                        var i = 0
                        for (thislessone in newthisday.split("newless".toRegex())
                            .toTypedArray()) { //Теперь пробегаемся по предметам данного дня
                            if (i != 0) {
                                val ScienceInformation =
                                    thislessone.replace("br ", "").split("br".toRegex())
                                        .toTypedArray()
                                var science = ScienceInformation[0]
                                science = science.replace("lessone", "Окно")
                                var ticher = ""
                                if (ScienceInformation.size > 1) ticher = ScienceInformation[1]
                                DayTimetable += "$i-ая: Предмет - $science\n$ticher\n\n"
                                ticher = ticher.replace("А-", "@А-").replace("А-", "@Б-")
                                    .replace("В-", "@В-").replace("Г-", "@Г-")
                                    .replace("Д-", "@Д-").replace("Е-", "@Е-")
                                    .replace("И-", "@И-").replace("K-", "@K-")
                                var Auditory: String
                                Auditory =
                                    if (ticher.split("@".toRegex()).toTypedArray().size == 2) {
                                        "Аудитория: " + ticher.split("@".toRegex())
                                            .toTypedArray()[1]
                                    } else "Аудитория: Дома" //На случай если пары нет
                                ticher = ticher.split("@".toRegex()).toTypedArray()[0]
                                ticher = if (ticher.length > 0) {
                                    "Преподаватель: $ticher"
                                } else {
                                    "Самоподготовка"
                                }
                                if (i == 1) {
                                    less[i - 1] = "1-ая (8:00-9:35) $science"
                                }
                                if (i == 2) {
                                    less[i - 1] = "2-ая (9:50-11:25) $science"
                                }
                                if (i == 3) {
                                    less[i - 1] = "3-ая (11:55-13:30) $science"
                                }
                                if (i == 4) {
                                    less[i - 1] = "4-ая (13:45-15:20) $science"
                                }
                                if (i == 5) {
                                    less[i - 1] = "5-ая (15:50-17:25) $science"
                                }
                                if (i == 6) {
                                    less[i - 1] = "6-ая (17:40-19:15) $science"
                                }
                                if (i == 7) {
                                    less[i - 1] = "7-ая (19:30-21:05) $science"
                                }
                                tich[i - 1] = ticher
                                aud[i - 1] = Auditory
                            } else DayData =
                                thislessone //При i=0 в thislessone будет дата текущего дня
                            i++
                        }
                    }
                }
            }
        }
        timetable!!.setText(DayData) //Выводим дату
        for (i in 0..6) {
            timetable!!.setText(
                """
                    ${timetable!!.text}
                    ${less[i]}${tich[i]}${aud[i]}
                    """.trimIndent()
            ) //Вывод пары, препода и аудитории каждой пары (от нулевой до шестой)
        }
        weeknumber!!.text = "Сейчас $WeekNumber неделя" //Выводим номер неддели
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        offline = false // Работаем в онлайн режиме
        count = 0
        weeknumber = findViewById(R.id.WeekNumber)
        timetable = findViewById(R.id.timetable)
        next = findViewById(R.id.next)
        down = findViewById(R.id.down)
        val getting = getting()
        getting.execute()
        //События для кнопок назад и вперёд
        next!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                count++
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, count)
                val dayformat = calendar.time
                val format = SimpleDateFormat("dd MMMM")
                formating(format.format(dayformat))
            }
        })
        down!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                count--
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, count)
                val dayformat = calendar.time
                val format = SimpleDateFormat("dd MMMM")
                formating(format.format(dayformat))
            }
        })
    }

    internal inner class getting:
        AsyncTask<String?, String?, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            //В этом методе код перед началом выполнения фонового процесса
            supportActionBar!!.title = "Загрузка..."
        }

        protected override fun doInBackground(vararg params: String?): String {
            /*Этот метод выполняется в фоне
            Тут мы обращаемся к сайту и вытаскиваем его html код
            */
            var answer = "" // В эту переменную мы будем класть ответ от сайта. Пока что она пустая
            val url = "https://ictis.sfedu.ru/rasp/HTML/82.htm" // Адрес сайта с расписанием
            var document: Document? = null
            try {
                document = Jsoup.connect(url).get() // Коннектимся и получаем страницу
                answer = document.body().html() // Получаем код из тега body страницы
            } catch (e: IOException) {
                // Если произошла ошибка, значит вероятнее всего, отсутствует соединение с интернетом
                // Загружаем в переменную answer офлайн версию из txt файла
                try {
                    val read = BufferedReader(InputStreamReader(openFileInput("timetable.txt")))
                    var str = ""
                    while (read.readLine().also { str = it } != null) {
                        answer += str
                    }
                    read.close()
                    offline = true //работаем в оффлайн режиме
                } catch (ex: FileNotFoundException) {
                    //Если файла с сохранённым расписанием нет, то записываем в answer пустоту
                    answer = ""
                    ex.printStackTrace()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
            }
            //Убираю лишний текст из html
            //Заменяю html код отсутствия пары на запись nolessone
            //Убираю двойные пробелы
            answer = answer.replace("Пары", "")
                .replace("Время", "")
                .replace("<br>", "br")
                .replace(
                    "<font face=\"Arial\" size=\"1\"></font><p align=\"CENTER\"><font face=\"Arial\" size=\"1\"></font>",
                    "nolessone"
                )
                .replace("  ", "")
            return Jsoup.parse(answer)
                .text() //Вытаскиваем текст из кода в переменной answer и передаём в UI-поток
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            /*Этот метод выполняется при завершении фонового кода
            Сюда возвращаются данные из потока
             */request = "" //Начинаем формировать ответ
            // Записываем содержимое, в файл timetable.txt, в котором будем хранить оффлайн версию расписания
            try {
                val writer = BufferedWriter(
                    OutputStreamWriter(
                        openFileOutput(
                            "timetable.txt",
                            MODE_PRIVATE
                        )
                    )
                )
                writer.write(result)
                writer.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            var start = false
            for (str in result.split("Неделя: ".toRegex()).toTypedArray()) {
                if (start) {
                    //В начало каждой недели добавляем слово newweek и добавляем в request
                    request += """
                        newweek${str.split("Расписание".toRegex()).toTypedArray()[0]}
                        
                        """.trimIndent()
                }
                start = true
            }
            // Добавляем к дням недели приставку newday, для дальнейшей разбивки строки
            request = request!!.replace("Пнд", "newdayПнд").replace("Втр", "newdayВтр")
                .replace("Срд", "newdayСрд").replace("Чтв", "newdayЧтв")
                .replace("Птн", "newdayПтн").replace("Сбт", "newdayСбт")
            /*Получаем дату дня
            Если count = 0, то вернётся дата сегодняшнего дня
            Если count = -1, то вчерашнего
            Если count = 1, то завтрашнего и т.д
             */
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, count)
            val dayformat = calendar.time
            val format = SimpleDateFormat("dd MMMM")
            //Вызываем функцию, которая будет заниматься представлением данных
            formating(format.format(dayformat))
            if (offline && result != "") {
                //Уведомляем пользователя, что загружена оффлайн версия расписания
                Toast.makeText(
                    applicationContext,
                    "Загружена оффлайн версия расписания!",
                    Toast.LENGTH_LONG
                ).show()
            }
            //Если наш ответ равен пустоте, значит произошла ошибка
            if (result == "") {
                Toast.makeText(applicationContext, "Произошла ошибка!", Toast.LENGTH_LONG).show()
            }
            supportActionBar!!.title = "Готово"
        }
    }
}