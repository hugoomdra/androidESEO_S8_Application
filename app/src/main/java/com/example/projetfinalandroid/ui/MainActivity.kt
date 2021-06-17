package com.example.projetfinalandroid.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.service.ApiService
import kotlinx.android.synthetic.main.activity_data.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), SensorEventListener{
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bouton actualiser
        btn_actualiser.setOnClickListener {
            val token = tf_token.text.toString();

            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            if (sensorLight != null){
                sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL)
            }else{
                LUMINOSITY = "X"
            }

            sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
            if (sensorPressure != null){
                sensorManager.registerListener(this, sensorPressure, SensorManager.SENSOR_DELAY_NORMAL)
            }else{
                PRESSURE = "X"
            }

            sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            if (sensorTemperature != null){
                sensorManager.registerListener(this, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL)
            }else{
                TEMPERATURE = "X"
            }

            getData();
            refreshData(token);
        }

        // Bouton voir données
        btn_see_data.setOnClickListener {
            val token = tf_token.text.toString();
            val intent = Intent(this, DataActivity::class.java)
            intent.putExtra("token", token);
            startActivity(intent)
        }

        // Bouton créer token
        btn_generate_token.setOnClickListener {
            startActivity(TokenActivity.getStartIntent(this));
        }

    }

    fun checkTokenNull(token: String) : Boolean{
        if(token == ""){
            Toast.makeText(this, getString(R.string.toast_ask_token), Toast.LENGTH_SHORT).show()
            return true;
        }else{
            return false;
        }
    }

    /*
    Fonction qui permet de récupérer les données et appeler l'API pour les refresh.
     */
    fun refreshData(token: String){

        val sensorManager : SensorManager
        val lightSensor : Sensor
        val lightEventListener : SensorEventListener
        val root : View
        val maxValue : Float

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager;
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val data = ApiService.instance.refreshData(
                    token,
                    LUMINOSITY,
                    BATTERY_LEVEL,
                    PRESSURE,
                    TEMPERATURE,
                    LOCATION
                )

                runOnUiThread{
                    Toast.makeText(this@MainActivity, getString(R.string.toast_data_refresh), Toast.LENGTH_SHORT).show()
                }

            }catch (err: Exception){

                runOnUiThread{
                    Toast.makeText(this@MainActivity, getString(R.string.toast_token_not_exist), Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    // TODO
                    // Permission non accepté, expliqué ici via une activité ou une dialog pourquoi nous avons besoin de la permission
                }
                return
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (hasPermission()) {
            val locationManager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager?
            locationManager?.run {
                locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)?.run {
                    LOCATION = this.latitude.toString() + "," + this.longitude.toString();
                }
            }
        }
    }


    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {

        if (!hasPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION
            )
        } else {
            getLocation()
        }
    }

    private fun getBatteryLevel(){
        val bm = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager;
        BATTERY_LEVEL = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toString()
    }

    private fun getData(){
        requestPermission();
        getBatteryLevel();
    }

    private fun getPressure(){
        sensorManager.registerListener(this, sensorPressure, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun getLuminosity(){
        sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun getTemperature(){
        sensorManager.registerListener(this, sensorTemperature, SensorManager.SENSOR_DELAY_NORMAL)
    }



    override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) {
            // Luminosité
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                LUMINOSITY = event.values[0].toString()
                sensorManager.unregisterListener(this, sensorLight)
            }
            // Pression
            if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                PRESSURE = event.values[0].toString()
                sensorManager.unregisterListener(this, sensorPressure)
            }
            // Temperature ambiante
            if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                TEMPERATURE = event.values[0].toString()
                sensorManager.unregisterListener(this, sensorTemperature)
            }
        }else {
            // rien à faire
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // rien à faire
    }

    companion object {

        const val PERMISSION_REQUEST_LOCATION = 9999

        var LOCATION = "X";
        var BATTERY_LEVEL = "X";
        var LUMINOSITY = "X";
        var PRESSURE = "X";
        var TEMPERATURE = "X";
        lateinit var sensorManager : SensorManager
        lateinit var sensorLight: Sensor
        lateinit var sensorPressure: Sensor
        lateinit var sensorTemperature: Sensor

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}