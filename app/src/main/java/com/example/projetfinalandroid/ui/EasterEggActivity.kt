package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetfinalandroid.R
import kotlinx.android.synthetic.main.activity_easter_egg.*

class EasterEggActivity : AppCompatActivity() {

    // la bonne réponse au jeu
    val bonneReponse = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easter_egg)

        supportActionBar?.apply {
            setTitle(getString(R.string.topbar_easter_egg))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        btn_soumettre.setOnClickListener {
            val nombre = edit_text.text.toString().toInt()

            // Logique de l'application pour afficher les bons textes
            if (nombre == bonneReponse) {
                text_reponse.setTextColor(getColor(R.color.eseo_blue))
                text_reponse.setText(getString(R.string.easter_egg_bonne_reponse))
            } else if (nombre > bonneReponse) {
                text_reponse.setTextColor(getColor(R.color.eseo_red))
                text_reponse.setText(getString(R.string.easter_egg_mauvaise_reponse_1))
            } else if (nombre >= 15) {
                text_reponse.setTextColor(getColor(R.color.eseo_red))
                text_reponse.setText(getString(R.string.easter_egg_mauvaise_reponse_2))
            } else if (nombre >= 10) {
                text_reponse.setTextColor(getColor(R.color.eseo_red))
                text_reponse.setText(getString(R.string.easter_egg_mauvaise_reponse_3))
            } else if (nombre >= 5) {
                text_reponse.setTextColor(getColor(R.color.eseo_red))
                text_reponse.setText(getString(R.string.easter_egg_mauvaise_reponse_4))
            } else {
                text_reponse.setTextColor(getColor(R.color.eseo_red))
                text_reponse.setText(getString(R.string.easter_egg_mauvaise_reponse_5))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, EasterEggActivity::class.java)
        }
    }
}