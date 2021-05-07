package com.example.qtracker.profile

import android.content.ClipData
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qtracker.R

class ProfileViewModel (val context: Context) : ViewModel() {

    val name = MutableLiveData("")
    val group = MutableLiveData("")
    val subgroup = MutableLiveData("")
    val password = MutableLiveData("")

}