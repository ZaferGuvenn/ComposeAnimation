package com.duovialove.composeanimation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {

    var userName by mutableStateOf("null")
        private set

    fun changeYourName(newName: String){
        userName = newName
    }
}