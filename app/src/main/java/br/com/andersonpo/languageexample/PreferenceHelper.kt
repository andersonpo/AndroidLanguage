package br.com.andersonpo.languageexample

import android.content.Context
import android.content.SharedPreferences
import java.lang.UnsupportedOperationException

object PreferenceHelper {

    fun defaultPrefs(context: Context): SharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit {it.putString(key, value)}
            is Int -> edit {it.putInt(key, value)}
            is Boolean -> edit {it.putBoolean(key, value)}
            is Float -> edit {it.putFloat(key, value)}
            is Long -> edit {it.putLong(key, value)}
            else -> throw  UnsupportedOperationException("Not implemented")
        }
    }
}