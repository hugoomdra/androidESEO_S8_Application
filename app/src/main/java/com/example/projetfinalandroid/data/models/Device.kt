package com.example.projetfinalandroid.data.models

import java.util.*

data class Device (
    var id : Int,
    var nom : String,
    var token: String,
    var created_at: String,
    var update_at: String,
){

    override fun toString(): String {
        return super.toString()
    }

    fun dataInfo(): String {
        return "$nom - $token"
    }


}