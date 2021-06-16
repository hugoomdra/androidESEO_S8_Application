package com.example.projetfinalandroid.data.models

data class Data (
    var device_id : Int,
    var luminosity: Int,
    var battery_level: Int,
    var pressure: Int,
    var temperature: Int,
    var position: String
){

    override fun toString(): String {
        return super.toString()
    }

    fun dataInfo(): String {
        return "$luminosity $luminosity $battery_level $pressure $temperature $position"
    }


}