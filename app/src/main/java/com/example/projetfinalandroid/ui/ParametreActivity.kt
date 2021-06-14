package com.example.projetfinalandroid.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.projetfinalandroid.R
import com.example.projetfinalandroid.data.ParametreAdapter
import kotlinx.android.synthetic.main.activity_parametre.*

class ParametreActivity : AppCompatActivity() {

    data class ParametreItem(val name: String, val onClick: (() -> Unit), val icone: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parametre)

        supportActionBar?.apply {
            setTitle(getString(R.string.topbar_parametre))
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // Liste des paramères à passer à l'adapter
        val settings = arrayOf(
            ParametreItem(getString(R.string.parametre_list_1),
                {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            }, R.drawable.ic_baseline_settings_24_blue),
            ParametreItem(getString(R.string.parametre_list_2), {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }, R.drawable.ic_baseline_edit_location_alt_24),
            ParametreItem(getString(R.string.parametre_list_3), {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:47.492884574915365,-0.5509639806591626")));
            }, R.drawable.ic_baseline_map_24_blue),
            ParametreItem(getString(R.string.parametre_list_4), {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://eseo.fr/")))
            }, R.drawable.ic_baseline_school_24),
            ParametreItem(getString(R.string.parametre_list_5), {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:hugoomdra@gmail.com?Subject="
                        + Uri.encode(getString(R.string.parametre_mail_sujet))
                        + "&Body="
                        + Uri.encode(getString(R.string.parametre_mail_message)))))
            }, R.drawable.ic_baseline_email_24_blue),
            ParametreItem(getString(R.string.parametre_list_6), {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/hugo-madureira/")))
            }, R.drawable.ic_baseline_account_circle_24_blue),

        )

        recyclerView.adapter = ParametreAdapter(settings)

    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ParametreActivity::class.java)
        }
    }
}