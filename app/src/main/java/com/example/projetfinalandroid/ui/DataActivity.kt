package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.service.ApiService
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round

class DataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        val token = intent.getStringExtra("token").toString()

        supportActionBar?.apply {
            setTitle(getString(R.string.topbar_data_activity) + token)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        getData(token);

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /*
    Cette fonction permet de récupérer les données et les afficher
     */
    fun getData(token : String){

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val array = ApiService.instance.readData(token);

                if (array.size != 0){


                    runOnUiThread{
                        // position round
                        val position = array[array.size - 1].position
                        if (position.contains(",")){
                            val positions = array[array.size - 1].position.split(',')
                            val lat = positions[0].toFloat()
                            val lon = positions[1].toFloat()
                            val df = DecimalFormat("#.##")
                            df.roundingMode = RoundingMode.CEILING
                            val latDf = df.format(lat);
                            val lonDf = df.format(lon);
                            txt_position.text = latDf.toString() + " , " + lonDf.toString() + "";
                            rl_position.setOnClickListener({
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lon)))
                            })
                        }else{
                            txt_position.text = position;

                        }

                        txt_luminosity.text = array[array.size - 1].luminosity.toString() + " lux";
                        txt_battery_level.text = array[array.size - 1].battery_level.toString() + " %";
                        txt_pressure.text = array[array.size - 1].pressure.toString() + " hPa";
                        txt_temperature.text = array[array.size - 1].temperature.toString() + " °C";
                        txt_date.text = array[array.size - 1].date.toString() + "";


                    }
                }else{
                    runOnUiThread{
                        runOnUiThread{
                            txt_luminosity.text = "X";
                            txt_battery_level.text = "X";
                            txt_pressure.text = "X";
                            txt_temperature.text = "X";
                            txt_position.text = "X";
                            txt_date.text = "X";
                        }

                        Toast.makeText(this@DataActivity, getString(R.string.toast_no_data), Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (err : Exception){
                runOnUiThread{
                    Toast.makeText(this@DataActivity, getString(R.string.toast_error), Toast.LENGTH_SHORT).show()
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