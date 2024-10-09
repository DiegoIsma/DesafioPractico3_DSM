package com.example.recursosaprendizaje

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditRecursoActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var recurso: Recurso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recurso)

        // Inicializar apiService (Retrofit o tu implementación de API)
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService::class.java)

        // Obtener el recurso pasado desde el Intent
        recurso = intent.getParcelableExtra("RECURSO")!!

        // Inicializar los campos del layout con los valores del recurso actual
        val editNombre = findViewById<EditText>(R.id.editNombre)
        val editDescripcion = findViewById<EditText>(R.id.editDescripcion)
        val editTipo = findViewById<EditText>(R.id.editTipo)
        val editEnlace = findViewById<EditText>(R.id.editEnlace)

        editNombre.setText(recurso.name)
        editDescripcion.setText(recurso.description)
        editTipo.setText(recurso.tipo)
        editEnlace.setText(recurso.enlace)

        // Configurar el botón de guardar
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            // Obtener los valores actualizados desde los campos de entrada
            val nombre = editNombre.text.toString()
            val descripcion = editDescripcion.text.toString()
            val tipo = editTipo.text.toString()
            val enlace = editEnlace.text.toString()

            // Crear el recurso actualizado
            val recursoActualizado = Recurso(
                recurso.id,
                nombre,
                descripcion,
                tipo,
                enlace,
                recurso.imagen
            )

            // Llamar a la API para actualizar el recurso
            apiService.updateRecurso(recurso.id, recursoActualizado)
                .enqueue(object : Callback<Recurso> {
                    override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                        if (response.isSuccessful) {
                            // Recurso actualizado exitosamente
                            finish()  // Regresar a la pantalla anterior
                        } else {
                            // Manejo de error en la respuesta
                            Log.e("EditRecursoActivity", "Error al actualizar: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<Recurso>, t: Throwable) {
                        // Manejo de errores en la llamada
                        Log.e("EditRecursoActivity", "Fallo la llamada API", t)
                    }
                })
        }
    }
}
