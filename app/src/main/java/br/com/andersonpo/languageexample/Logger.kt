package br.com.andersonpo.languageexample

import android.util.Log
import java.lang.Exception

object Logger {
    var tag: String = "LOGGER"

    fun Debug(message: String) {
        Log.d(tag, message)
    }

    fun Error(message: String, error: Exception) {
        Log.e(tag, message, error)
    }

}