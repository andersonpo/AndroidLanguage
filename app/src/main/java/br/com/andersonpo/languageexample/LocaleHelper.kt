package br.com.andersonpo.languageexample

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import java.lang.Exception
import java.util.*

object LocaleHelper {

    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
    private var sharedPreference: SharedPreferences? = null

    fun getCurrentLanguage(context: Context, defaultLanguage: String = "en_US"): String {
        if (sharedPreference == null)
            sharedPreference = PreferenceHelper.defaultPrefs(context)

        var currentLanguage = sharedPreference!!.getString(SELECTED_LANGUAGE, defaultLanguage)
        if (currentLanguage == null)
            currentLanguage = defaultLanguage

        return currentLanguage
    }

    private fun saveLanguagePreference(context: Context, language: String) {
        if (sharedPreference == null)
            sharedPreference = PreferenceHelper.defaultPrefs(context)

        sharedPreference!!.edit().putString(SELECTED_LANGUAGE, language).apply()
    }

    fun setLocale(context: Context?): Context {
        val language = getCurrentLanguage(context!!)
        val locale = getLocale(language)
        return updateResources(context, locale)
    }

    fun setNewLocale(context: Context, locale: Locale) {
        saveLanguagePreference(context, "${locale.language}_${locale.country}")
        updateResources(context, locale)
    }

    fun getLocale(language: String, defaultLanguage: String = "en_US"): Locale {
        var locale: Locale
        val languageArray = if (language.isNotEmpty() && language.contains("_"))
            language.split("_")
        else {
            Logger.Debug("LocaleHelper - getLocale - USE defaultLanguage")
            defaultLanguage.split("_")
        }

        locale = try {
            Locale(languageArray[0], languageArray[1])
        } catch (ex: Exception) {
            Logger.Error("LocaleHelper - getLocale - language $language - Error ${ex.message}", ex)
            //getLocale(defaultLanguage, "en_US")
            Locale("en", "US")
        }
        Logger.Debug("LocaleHelper - getLocale ${locale.language} - ${locale.country}")
        return locale
    }

    @Suppress("DEPRECATION")
    fun updateResources(context: Context, locale: Locale): Context {
        Logger.Debug("LocaleHelper - updateResources locale ${locale.language} - ${locale.country}")

        var newContext = context
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale)
            newContext = context.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            context.resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return newContext
    }

}