package com.example.projetfinalandroid.data.models

import java.util.*

data class Data (
    var id : Int,
    var device_id : Int,
    var luminosity: String,
    var battery_level: String,
    var pressure: String,
    var temperature: String,
    var position: String,
    var created_at: String,
    var update_at: String,
    var date: String,
    var nom: String,
){

    override fun toString(): String {
        return super.toString()
    }

    fun dataInfo(): String {
        return "$luminosity $luminosity $battery_level $pressure $temperature $position"
    }


}