package com.example.recursosaprendizaje

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

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
    fun deleteRecurso(@Path("id") id: String): Call<ResponseBody>

}