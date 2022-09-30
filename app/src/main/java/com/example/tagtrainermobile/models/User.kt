package com.example.tagtrainermobile.models

data class User (
    val email: String,
    val password: String,
    var isLogged: Boolean
    ) {
    object sigleUser {
        var instance = User("joao.silva@gmail.com", "1111", false)
    }
}

