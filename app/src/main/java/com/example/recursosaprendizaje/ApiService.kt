package com.example.recursosaprendizaje

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("Recursos")
    fun getAllRecursos(): Call<List<Recurso>>

    @GET("Recursos/{id}")
    fun getRecursoById(@Path("id") id: String): Call<Recurso>

    @POST("Recursos")
    fun addRecurso(@Body recurso: Recurso): Call<Recurso>

    @PUT("Recursos/{id}")
    fun updateRecurso(@Path("id") id: String, @Body recurso: Recurso): Call<Recurso>

    @DELETE("Recursos/{id}")
    fun deleteRecurso(@Path("id") id: String): Call<Void>

}