package com.example.projetfinalandroid.data.models

import java.util.*

data class Data (
    var id : Int,
    var device_id : Int,
    var luminosity: Int,
    var battery_level: Int,
    var pressure: Int,
    var temperature: Int,
    var position: String,
    var created_at: String,
    var update_at: String,
    var date: String,
){

    override fun toString(): String {
        return super.toString()
    }

    fun dataInfo(): String {
        return "$luminosity $luminosity $battery_level $pressure $temperature $position"
    }


}