package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.service.ApiService
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        supportActionBar?.apply {
            setTitle(getString(R.string.topbar_historique))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val token = intent.getStringExtra("token").toString()

        getData(token);




    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun getData(token : String){

        CoroutineScope(Dispatchers.IO).launch {
            try{
                val array = ApiService.instance.readData(token);

                if (array.size != 0){

                    runOnUiThread{
                        txt_luminosity.text = array[array.size - 1].luminosity.toString() + " lux";
                        txt_battery_level.text = array[array.size - 1].battery_level.toString() + " %";
                        txt_pressure.text = array[array.size - 1].pressure.toString() + " Pa";
                        txt_temperature.text = array[array.size - 1].temperature.toString() + " °C";
                        txt_position.text = array[array.size - 1].position.toString() + "";
                        txt_date.text = array[array.size - 1].date.toString() + "";
                    }
                }else{
                    runOnUiThread{
                        Toast.makeText(this@DataActivity, "Aucune donnée trouvée.", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (err: Exception){
                runOnUiThread{
                    Toast.makeText(this@DataActivity, "ERREUR...", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, DataActivity::class.java)
        }
    }

}