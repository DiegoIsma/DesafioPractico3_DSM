package com.example.recursosaprendizaje
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://6701b49fb52042b542d86557.mockapi.io/"

    // Singleton de Retrofit
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Para convertir JSON automáticamente a objetos
            .build()
    }

    // Método para obtener una instancia de la API service
    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }
}