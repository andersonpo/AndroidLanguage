package br.com.andersonpo.languageexample

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate

open class ApplicationClass: Application() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.setLocale(base))
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleHelper.setLocale(this)

        @Suppress("DEPRECATION")
        Logger.Debug("ApplicationClass - onConfigurationChanged newConfig ${newConfig.locale.language}")
    }

}