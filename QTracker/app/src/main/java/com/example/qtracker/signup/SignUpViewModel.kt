package com.example.qtracker.signup

import android.content.ClipData
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qtracker.R
import com.example.qtracker.SingleLiveEvent
import com.example.qtracker.dataclasses.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.sign_up_fragment.view.*
import kotlinx.coroutines.launch

class SignUpViewModel (val context: Context) : ViewModel() {

    private lateinit var mDataBase: DatabaseReference
    private var USER_KEY: String = "User"

    private val _errorMessage = MutableLiveData("")
    val errorMessage:LiveData<String> = _errorMessage

    val name = MutableLiveData("")
    //val group = MutableLiveData("")
    //val subgroup = MutableLiveData("")
    val password = MutableLiveData("")
    val repeatPassword = MutableLiveData("")

    private lateinit var group: String
    private lateinit var subgroup: String

    var items: ArrayList<ClipData.Item> = ArrayList()
    var itemPosition = MutableLiveData<Int>()

    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private val _userSignedUp = SingleLiveEvent<Boolean>()
    val userSignedUp:LiveData<Boolean> = _userSignedUp

    fun init(group: String, subgroup: String){
        this.group = group
        this.subgroup = subgroup
    }


    fun signUp(){
        viewModelScope.launch {
            _loading.value =true
            if (inputDataIsValid()){
                saveData()
                _userSignedUp.value = true
            }
            _loading.value=false
        }
    }

    private fun saveData() {
        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY)

        val id: String? = mDataBase.key
        val newUser = User(id.toString(), name.value!!, password.value!!, group, subgroup)

        mDataBase.push().setValue(newUser)
    }

    private fun inputDataIsValid():Boolean {

        if (name.value.isNullOrEmpty()){
            _errorMessage.value = context.resources.getString(R.string.error_incorrect_name)
            return false
        }
        if (group.isNullOrEmpty()){
            _errorMessage.value = context.resources.getString(R.string.error_incorrect_group)
            return false
        }
        if (subgroup.isNullOrEmpty()){
            _errorMessage.value = context.resources.getString(R.string.error_incorrect_subgroup)
            return false
        }
        if (password.value.isNullOrEmpty() || password.value!!.length<=2){
            _errorMessage.value = context.resources.getString(R.string.error_incorrect_password)
            return false
        }
        if (repeatPassword.value.isNullOrEmpty()){
            _errorMessage.value = context.resources.getString(R.string.error_password_not_repeated)
            return false
        }
        if (repeatPassword.value!=password.value){
            _errorMessage.value = context.resources.getString(R.string.error_passwords_dont_match)
            return false
        }
        return true
    }

    fun String?.isEmailValid(): Boolean {
        return !isNullOrEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}