package com.example.recursosaprendizaje

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var recursoAdapter: RecursoAdapter
    private var recursosList = mutableListOf<Recurso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)  // Asegúrate de que este ID coincida con tu layout XML
        searchView = findViewById(R.id.searchView)      // Obtener referencia al SearchView
        recyclerView.layoutManager = LinearLayoutManager(this)  // Inicializar el layout manager

        val retrofit = Retrofit.Builder()
            .baseUrl("https://6701b49fb52042b542d86557.mockapi.io/") // URL base de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        getRecursosFromApi()

        // Configuración del SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recursoAdapter.filter(newText)  // Llama al método filter aquí
                return true
            }
        })
    }

    private fun getRecursosFromApi() {
        apiService.getAllRecursos().enqueue(object : Callback<List<Recurso>> {
            override fun onResponse(call: Call<List<Recurso>>, response: Response<List<Recurso>>) {
                if (response.isSuccessful) {
                    recursosList = response.body()?.toMutableList() ?: mutableListOf()
                    recursoAdapter = RecursoAdapter(recursosList)  // Asignar el adaptador aquí
                    recyclerView.adapter = recursoAdapter
                } else {
                    Log.e("MainActivity", "Error en la respuesta de la API: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Recurso>>, t: Throwable) {
                Log.e("MainActivity", "Error al cargar recursos", t)
            }
        })
    }
}
