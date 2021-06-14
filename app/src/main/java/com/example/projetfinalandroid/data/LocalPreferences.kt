package com.example.projetfinalandroid.data

import android.content.Context
import android.content.SharedPreferences

class LocalPreferences private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

    companion object {
        private var INSTANCE: LocalPreferences? = null

        fun getInstance(context: Context): LocalPreferences {
            return INSTANCE?.let {
                INSTANCE
            } ?: run {
                INSTANCE = LocalPreferences(context)
                return INSTANCE!!
            }
        }
    }

    // Fonction qui permet d'ajouter une entrée dans la collection "histories"
    fun addToHistory(newEntry: String){
        val history = this.getHistory()
        history?.add(newEntry)
        sharedPreferences.edit().putStringSet("histories", history).apply()
    }

    // Fonction qui permet de récupérer la collection "histories"
    fun getHistory(): MutableSet<String>? {
        return sharedPreferences.getStringSet("histories",  mutableSetOf<String>().toMutableSet() )
    }

    // Fonction qui permet de vider la collection "histories"
    fun deleteAllHistory() {
        sharedPreferences.edit().clear().apply()
    }

    // Fonction qui permet de vérifier si la collection "histories" est vide
    fun nullHistory(): Boolean {
        if (sharedPreferences.getStringSet("histories", mutableSetOf<String>())?.isEmpty() != true) {
            return false
        } else
            return true
    }
}




