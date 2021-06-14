package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.data.LocalPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bouton localisation
        btn_localisation.setOnClickListener { ;
            startActivity(LocalisationActivity.getStartIntent(this))
//            finish()
        }

        // Bouton parametre
        btn_parametre.setOnClickListener{
            startActivity(ParametreActivity.getStartIntent(this))
        }

        // Bouton easter egg
        btn_easter_egg.setOnClickListener{
            startActivity(EasterEggActivity.getStartIntent(this))

        }
    }

    override fun onResume() {
        super.onResume()
        // Desactivation du bouton historique si jamais la liste est vide.
        if (LocalPreferences.getInstance(this).nullHistory()){
            logo_history.setImageResource(R.drawable.ic_baseline_history_24);
            btn_historique.setEnabled(false)
            text_history.setTextColor(getColor(R.color.disable))
        }else{
            logo_history.setImageResource(R.drawable.ic_baseline_history_24_active);
            text_history.setTextColor(getColor(R.color.eseo_red))
            btn_historique.setEnabled(true)
            btn_historique.setOnClickListener{
                startActivity(HistoriqueActivity.getStartIntent(this))
//            if(LocalPreferences.getInstance(this).getSaveStringValue() != null){
//                Toast.makeText(this, LocalPreferences.getInstance(this).getSaveStringValue(), Toast.LENGTH_SHORT).show()
//            }
            }
        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}