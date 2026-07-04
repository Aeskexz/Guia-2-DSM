package com.example.guia2_ejercicio2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    // 1. Variables
    lateinit var etNumero1: EditText
    lateinit var etNumero2: EditText
    lateinit var tvResultado: TextView
    lateinit var tvHistorial: TextView
    lateinit var btnSuma: Button
    lateinit var btnResta: Button
    lateinit var btnMultiplicacion: Button
    lateinit var btnDivision: Button
    lateinit var btnPorcentaje: Button
    lateinit var btnCuadrado: Button
    lateinit var btnRaiz: Button
    lateinit var btnLimpiar: Button

    var historial = mutableListOf<String>()

    // 2. Metodo del ciclo de vida del activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 3. Establecer archivo layout
        setContentView(R.layout.activity_main)

        // 4. Referencias a elementos de la UI
        etNumero1 = findViewById(R.id.etNumero1)
        etNumero2 = findViewById(R.id.etNumero2)
        tvResultado = findViewById(R.id.tvResultado)
        tvHistorial = findViewById(R.id.tvHistorial)
        btnSuma = findViewById(R.id.btnSuma)
        btnResta = findViewById(R.id.btnResta)
        btnMultiplicacion = findViewById(R.id.btnMultiplicacion)
        btnDivision = findViewById(R.id.btnDivision)
        btnPorcentaje = findViewById(R.id.btnPorcentaje)
        btnCuadrado = findViewById(R.id.btnCuadrado)
        btnRaiz = findViewById(R.id.btnRaiz)
        btnLimpiar = findViewById(R.id.btnLimpiar)

        tvResultado.text = "Resultado: 0.0"
        tvHistorial.text = "Historial:"

        // 5. Listeners de los botones
        btnSuma.setOnClickListener { realizarOperacion("+") }
        btnResta.setOnClickListener { realizarOperacion("-") }
        btnMultiplicacion.setOnClickListener { realizarOperacion("×") }
        btnDivision.setOnClickListener { realizarOperacion("÷") }
        btnPorcentaje.setOnClickListener { realizarOperacionUnaria("%") }
        btnCuadrado.setOnClickListener { realizarOperacionUnaria("²") }
        btnRaiz.setOnClickListener { realizarOperacionUnaria("√") }

        btnLimpiar.setOnClickListener {
            etNumero1.text.clear()
            etNumero2.text.clear()
            tvResultado.text = "Resultado: 0.0"
            historial.clear()
            tvHistorial.text = "Historial:"
            etNumero1.requestFocus()
            mostrarToast("Pantalla reiniciada")
        }
    }

    private fun realizarOperacion(operador: String) {
        val texto1 = etNumero1.text.toString().trim()
        val texto2 = etNumero2.text.toString().trim()

        if (texto1.isEmpty() || texto2.isEmpty()) {
            mostrarToast("Error, ambos campos son requeridos.")
            return
        }

        val numero1 = texto1.toDoubleOrNull()
        val numero2 = texto2.toDoubleOrNull()

        if (numero1 == null || numero2 == null) {
            mostrarToast("Error, ingrese números válidos.")
            return
        }

        var resultado = 0.0
        when (operador) {
            "+" -> {
                resultado = numero1 + numero2
                agregarHistorial("$numero1 + $numero2 = ${String.format("%.2f", resultado)}")
            }
            "-" -> {
                resultado = numero1 - numero2
                agregarHistorial("$numero1 - $numero2 = ${String.format("%.2f", resultado)}")
            }
            "×" -> {
                resultado = numero1 * numero2
                agregarHistorial("$numero1 × $numero2 = ${String.format("%.2f", resultado)}")
            }
            "÷" -> {
                if (numero2 == 0.0) {
                    mostrarToast("Error, no se puede dividir por cero.")
                    return
                }
                resultado = numero1 / numero2
                agregarHistorial("$numero1 ÷ $numero2 = ${String.format("%.2f", resultado)}")
            }
        }
        tvResultado.text = "Resultado: ${String.format("%.2f", resultado)}"
    }

    private fun realizarOperacionUnaria(operador: String) {
        val texto = etNumero1.text.toString().trim()

        if (texto.isEmpty()) {
            mostrarToast("Error, campo vacio.")
            return
        }

        val numero = texto.toDoubleOrNull()
        if (numero == null) {
            mostrarToast("Error, ingrese un número válido.")
            return
        }

        var resultado = 0.0
        when (operador) {
            "%" -> {
                resultado = numero / 100
                agregarHistorial("$numero % = ${String.format("%.2f", resultado)}")
            }
            "²" -> {
                resultado = numero * numero
                agregarHistorial("$numero² = ${String.format("%.2f", resultado)}")
            }
            "√" -> {
                if (numero < 0) {
                    mostrarToast("Error, no se puede calcular raíz de número negativo.")
                    return
                }
                resultado = sqrt(numero)
                agregarHistorial("√$numero = ${String.format("%.2f", resultado)}")
            }
        }
        tvResultado.text = "Resultado: ${String.format("%.2f", resultado)}"
    }

    private fun agregarHistorial(operacion: String) {
        historial.add(operacion)
        if (historial.size > 5) {
            historial.removeAt(0)
        }
        tvHistorial.text = "Historial:\n" + historial.joinToString("\n")
    }

    // Funcion auxiliar para mostrar mensaje emergente
    private fun mostrarToast(mensaje: String, duracion: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, mensaje, duracion).show()
    }
}