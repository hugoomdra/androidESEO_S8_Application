package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.service.ApiService
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_easter_egg.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bouton actualiser
        btn_actualiser.setOnClickListener {
            val token = tf_token.text.toString();
            refreshData(token);
//            finish()
        }

        // Bouton voir données
        btn_see_data.setOnClickListener {
            val token = tf_token.text.toString();
            val intent = Intent(this, DataActivity::class.java)
            intent.putExtra("token", token);
            startActivity(intent)
        }

    }

    fun checkTokenNull(token: String) : Boolean{
        if(token == ""){
            Toast.makeText(this, "Veuillez fournir un token", Toast.LENGTH_SHORT).show()
            return true;
        }else{
            return false;
        }
    }

    fun refreshData(token: String){

        val sensorManager : SensorManager
        val lightSensor : Sensor
        val lightEventListener : SensorEventListener
        val root : View
        val maxValue : Float

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager;
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)

        System.out.println(lightSensor)

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val data = ApiService.instance.refreshData(token, lightSensor.power.toString(), "14", "18", "10", "1")

                runOnUiThread{
                    Toast.makeText(this@MainActivity, "Données refresh", Toast.LENGTH_SHORT).show()
                }

            }catch (err: Exception){

                runOnUiThread{
                    Toast.makeText(this@MainActivity, "ERREUR...", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }





//    override fun onResume() {
//        super.onResume()
//        // Desactivation du bouton historique si jamais la liste est vide.
//        if (LocalPreferences.getInstance(this).nullHistory()){
//            logo_history.setImageResource(R.drawable.ic_baseline_history_24);
//            btn_historique.setEnabled(false)
//            text_history.setTextColor(getColor(R.color.disable))
//        }else{
//            logo_history.setImageResource(R.drawable.ic_baseline_history_24_active);
//            text_history.setTextColor(getColor(R.color.eseo_red))
//            btn_historique.setEnabled(true)
//            btn_historique.setOnClickListener{
//                startActivity(HistoriqueActivity.getStartIntent(this))
////            if(LocalPreferences.getInstance(this).getSaveStringValue() != null){
////                Toast.makeText(this, LocalPreferences.getInstance(this).getSaveStringValue(), Toast.LENGTH_SHORT).show()
////            }
//            }
//        }
//    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}