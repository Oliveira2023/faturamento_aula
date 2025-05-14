package com.dev_oliveira.faturamento_aula

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferences2: SharedPreferences
    private lateinit var yearPicker: NumberPicker
    private lateinit var radioGroup: RadioGroup
    private lateinit var confirmarButton: Button
    private lateinit var valor: EditText
    private lateinit var saldoAtual: TextView
    private lateinit var btnAddTitulo: Button
    private lateinit var nomeEmpresa: TextView

    private val PREFS_NAME = "MyPrefsFile"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        yearPicker = findViewById(R.id.yearPicker)
        confirmarButton = findViewById(R.id.executeButton)
        radioGroup = findViewById(R.id.radioGroup)
        valor = findViewById(R.id.valor)
        saldoAtual = findViewById(R.id.viewSaldo)
        btnAddTitulo = findViewById(R.id.btnAddTitulo)
        nomeEmpresa = findViewById(R.id.viewNomeEmpresa)

        yearPicker.minValue = 2020
        yearPicker.maxValue = 2030
        yearPicker.setOnValueChangedListener { _, _, newVal ->
            //Toast.makeText(this, "Year $newVal", Toast.LENGTH_SHORT).show()
            exibirSaldo(newVal)
        }
        confirmarButton.setOnClickListener {
            if (!valor.text.isNullOrEmpty()) {
                val floatValue = valor.text.toString().toFloatOrNull()

                val ano = getYear()


                val checkRadioButton = radioGroup.checkedRadioButtonId
                if (floatValue != null && checkRadioButton != -1) {
                    if (checkRadioButton == R.id.receita) {
                        adicionarValor(ano, floatValue)
                    } else {
                        removerValor(ano, floatValue)
                    }
                    valor.text.clear()
                }

                else {
                    Toast.makeText(this, "Selecione uma operação e um valor", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnAddTitulo.setOnClickListener {
            val intent = Intent(this, activity_personalizar::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        exibirSaldo(getYear())
    }
     override fun onResume() {
        super.onResume()

        sharedPreferences2 = getSharedPreferences(SettingsManager.ARQUIVO_PREFERENCIAS, MODE_PRIVATE)
        val nomeFantasia = sharedPreferences2.getString("nomeFantasia", "")
        if (!nomeFantasia.isNullOrEmpty()) {
            nomeEmpresa.text = nomeFantasia
        }
        //exibirSaldo(getYear())
    }
    fun getYear(): Int {
        //Toast.makeText(this, "Year ${yearPicker.value}", Toast.LENGTH_SHORT).show()
        return yearPicker.value
    }
    fun adicionarValor(ano: Int, valor: Float) {
        val saldo = sharedPreferences.getFloat(ano.toString(), 0f)
        val novoSaldo = saldo + valor
        with(sharedPreferences.edit()) {
            putFloat(ano.toString(), novoSaldo)
            apply()
        }
        exibirSaldo(ano)
    }
    fun removerValor(ano: Int, valor: Float) {
        val saldo = saldoAtual.text.toString().toFloatOrNull() ?: 0f
        if (saldo < valor) {
            Toast.makeText(this, "Saldo insuficiente", Toast.LENGTH_SHORT).show()
            return
        }
        val novoSaldo = saldo - valor
        with(sharedPreferences.edit()) {
            putFloat(ano.toString(), novoSaldo)
            apply()
        }
        exibirSaldo(ano)
    }
    fun exibirSaldo(ano: Int) {
        //Toast.makeText(this, "Saldo para o ano $ano", Toast.LENGTH_SHORT).show()
        val saldo = sharedPreferences.getFloat(ano.toString(), 0f)
        saldoAtual.text = "R$ " + String.format("%.2f", saldo)

    }

    companion object {
        val MyPrefsFile: String? = null
    }

}