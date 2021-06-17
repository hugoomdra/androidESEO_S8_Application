package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.service.ApiService
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_token.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TokenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token)


        supportActionBar?.apply {
            setTitle(getString(R.string.topbar_token_activity))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        btn_create.setOnClickListener({
            val nom = tf_nom.text.toString()
            val token = tf_token.text.toString()
            createToken(nom, token)
        })
    }


    /*
    Cette fonction permet d'appeler l'API pour générer un nouveau TOKEN.
     */
    fun createToken(nom : String, token : String){

        CoroutineScope(Dispatchers.IO).launch {
            try{
                val device = ApiService.instance.createToken(nom, token);
                finish()

                runOnUiThread {
                    Toast.makeText(this@TokenActivity, getString(R.string.toast_token_created), Toast.LENGTH_SHORT).show()
                }


            }catch (err: Exception){

                runOnUiThread {
                    Toast.makeText(this@TokenActivity, getString(R.string.toast_token_existing), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, TokenActivity::class.java)
        }
    }

}