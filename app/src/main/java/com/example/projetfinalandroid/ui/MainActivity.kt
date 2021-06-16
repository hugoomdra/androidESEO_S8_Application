package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.data.LocalPreferences
import com.example.projetfinalandroid.service.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bouton localisation
        btn_actualiser.setOnClickListener { ;
            refreshData();
//            finish()
        }
    }


    fun refreshData(){

        System.out.println("super test")

        // Votre code de d'habitude…
        CoroutineScope(Dispatchers.IO).launch {

            try{
                System.out.println("test test avant appel")
            val array = ApiService.instance.readData("123456789")
                System.out.println("test test apres appel")
                System.out.println(array)
            }catch (err : Exception){
                System.out.println("HHH")
            }

//            runCatching {
//
//
//                runOnUiThread{
//                    System.out.println("test test")
////                    dataSource.addAll(arrStatus)
//                    Toast.makeText(this@MainActivity, "Résultat de l'appel réseau", Toast.LENGTH_SHORT).show()
//                }
//            }
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