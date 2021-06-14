package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.data.HistoriqueAdapter
import com.example.projetfinalandroid.data.LocalPreferences
import kotlinx.android.synthetic.main.activity_historique.*

class HistoriqueActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historique)

        supportActionBar?.apply {
            setTitle(getString(R.string.topbar_historique))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // récupération de l'historique actuel
        val items : Array<String> = LocalPreferences.getInstance(this).getHistory()!!.toTypedArray()

        // Liste pour tester le RecyclerView
        // val res : Array<String> = arrayOf("super", "test", "oslo", "rayhko", "voxytech","unzinziin")

        // Init de l'adapter pour le recycler view
        recyclerView.adapter = HistoriqueAdapter(items);


        // Bouton pour vider la liste historique
        btn_delete_historique.setOnClickListener{
            LocalPreferences.getInstance(this).deleteAllHistory();
            finish()
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, HistoriqueActivity::class.java)
        }
    }
}