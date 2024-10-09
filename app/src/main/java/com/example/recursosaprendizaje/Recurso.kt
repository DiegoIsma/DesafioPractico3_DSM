package com.example.recursosaprendizaje
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recurso (
    val id: String,
    val name: String,
    val description: String,
    val tipo: String,
    val enlace: String,
    val imagen: String
) : Parcelable
