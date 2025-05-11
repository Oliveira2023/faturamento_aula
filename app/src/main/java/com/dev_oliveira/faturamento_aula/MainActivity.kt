package com.dev_oliveira.faturamento_aula

import android.os.Bundle
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var yearPicker: NumberPicker
    private lateinit var selectYearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        yearPicker = findViewById(R.id.yearPicker)
        selectYearButton = findViewById(R.id.executeButton)

        yearPicker.minValue = 2020
        yearPicker.maxValue = 2030
        yearPicker.setOnValueChangedListener { _, _, newVal ->
            Toast.makeText(this, "Year $newVal", Toast.LENGTH_SHORT).show()
        }
        fun getYear(): Int {
            Toast.makeText(this, "Year ${yearPicker.value}", Toast.LENGTH_SHORT).show()
            return yearPicker.value
        }

        selectYearButton.setOnClickListener {
            getYear()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}