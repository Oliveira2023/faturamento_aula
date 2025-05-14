package com.dev_oliveira.faturamento_aula

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {
    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences("ARQUIVO_PREFERENCIAS", Context.MODE_PRIVATE)
    }

    fun setAno(ano: Int) {
        with(sharedPreferences.edit()) {
            putInt("ano", ano)
            apply()
        }
    }

    fun getAno(): Int {
        return sharedPreferences.getInt("ano", 0)
    }

    companion object {
        const val ARQUIVO_PREFERENCIAS = "meusDados"
    }
}