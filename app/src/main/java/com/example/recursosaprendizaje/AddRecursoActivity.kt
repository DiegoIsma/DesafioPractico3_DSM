package com.example.recursosaprendizaje

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddRecursoActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService

    private lateinit var editName: EditText
    private lateinit var editDescription: EditText
    private lateinit var editTipo: EditText
    private lateinit var editEnlace: EditText
    private lateinit var editImagen: EditText
    private lateinit var buttonAdd: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recurso)

        // Inicializar vistas
        editName = findViewById(R.id.editName)
        editDescription = findViewById(R.id.editDescription)
        editTipo = findViewById(R.id.editTipo)
        editEnlace = findViewById(R.id.editEnlace)
        editImagen = findViewById(R.id.editImagen)
        buttonAdd = findViewById(R.id.buttonAdd)

        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://6701b49fb52042b542d86557.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        // Configurar el botón de agregar
        buttonAdd.setOnClickListener {
            agregarRecurso()
        }
    }

    private fun agregarRecurso() {
        val name = editName.text.toString().trim()
        val description = editDescription.text.toString().trim()
        val tipo = editTipo.text.toString().trim()
        val enlace = editEnlace.text.toString().trim()
        val imagen = editImagen.text.toString().trim()

        // Validar campos
        if (name.isEmpty() || description.isEmpty() || tipo.isEmpty() || enlace.isEmpty() || imagen.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear objeto Recurso
        val nuevoRecurso = Recurso(
            name = name,
            description = description,
            tipo = tipo,
            enlace = enlace,
            imagen = imagen,
            id = "" // La API generará el ID
        )

        // Realizar la petición POST
        apiService.addRecurso(nuevoRecurso).enqueue(object : Callback<Recurso> {
            override fun onResponse(call: Call<Recurso>, response: Response<Recurso>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddRecursoActivity, "Recurso añadido correctamente", Toast.LENGTH_SHORT).show()
                    finish() // Cerrar la actividad y regresar a MainActivity
                } else {
                    Toast.makeText(this@AddRecursoActivity, "Error al añadir el recurso: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("AddRecursoActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Recurso>, t: Throwable) {
                Toast.makeText(this@AddRecursoActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("AddRecursoActivity", "Error al añadir recurso", t)
            }
        })
    }
}