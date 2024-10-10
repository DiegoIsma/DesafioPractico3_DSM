package com.example.recursosaprendizaje

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.ResponseBody
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
    private lateinit var fabAdd: FloatingActionButton
    private var recursosList = mutableListOf<Recurso>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        fabAdd = findViewById(R.id.fabAdd)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://6701b49fb52042b542d86557.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)

        getRecursosFromApi()


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recursoAdapter.filter(newText)
                return true
            }
        })
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddRecursoActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        getRecursosFromApi()
    }

    private fun getRecursosFromApi() {
        apiService.getAllRecursos().enqueue(object : Callback<List<Recurso>> {
            override fun onResponse(call: Call<List<Recurso>>, response: Response<List<Recurso>>) {
                if (response.isSuccessful) {
                    val recursos = response.body() ?: emptyList()
                    recursoAdapter = RecursoAdapter(recursos,
                        onEditar = { recurso -> editarRecurso(recurso) },
                        onItemClick = { recurso ->

                        },
                        onEliminar = { recurso -> eliminarRecurso(recurso) }
                    )
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
    private fun editarRecurso(recurso: Recurso) {
        val intent = Intent(this, EditRecursoActivity::class.java)
        intent.putExtra("RECURSO", recurso)
        startActivity(intent)
    }

    private fun eliminarRecurso(recurso: Recurso) {
        apiService.deleteRecurso(recurso.id).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    getRecursosFromApi()
                } else {
                    Log.e("MainActivity", "Error al eliminar el recurso: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("MainActivity", "Error al eliminar el recurso", t)
            }
        })
    }
}
