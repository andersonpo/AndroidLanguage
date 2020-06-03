package br.com.andersonpo.languageexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentLanguage = LocaleHelper.getCurrentLanguage(this)
        Logger.Debug("onCreate currentLanguage $currentLanguage")
        LocaleHelper.setNewLocale(this, LocaleHelper.getLocale(currentLanguage))

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnChangeLanguage).text = "Language = $currentLanguage"
        findViewById<Button>(R.id.btnChangeLanguage).setOnClickListener(View.OnClickListener {
            val currentLanguage = LocaleHelper.getCurrentLanguage(this)
            val newLanguage = if (currentLanguage.toLowerCase() == "en" || currentLanguage.toLowerCase() == "en_us") "pt_BR" else "en_US"
            LocaleHelper.setNewLocale(this, LocaleHelper.getLocale(newLanguage))
            Logger.Debug("newLanguage = $newLanguage")
            recreate()
        })
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase))

        @Suppress("DEPRECATION")
        Logger.Debug("attachBaseContext newBase ${newBase!!.resources.configuration.locale.language}")
    }
}