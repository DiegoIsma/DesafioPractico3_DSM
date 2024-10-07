package com.example.recursosaprendizaje

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Locale

class RecursoAdapter(private var recursos: List<Recurso>) :
    RecyclerView.Adapter<RecursoAdapter.RecursoViewHolder>() {

    private var recursosFiltrados: List<Recurso> = recursos

    class RecursoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.nombre)
        val descripcion: TextView = itemView.findViewById(R.id.descripcion)
        val tipo: TextView = itemView.findViewById(R.id.tipo)
        val enlace: TextView = itemView.findViewById(R.id.enlace)
        val imagen: ImageView = itemView.findViewById(R.id.imagen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecursoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recurso, parent, false)
        return RecursoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecursoViewHolder, position: Int) {
        val recurso = recursosFiltrados[position]
        holder.nombre.text = recurso.name
        holder.descripcion.text = recurso.description
        holder.tipo.text = recurso.tipo
        holder.enlace.text = recurso.enlace

        // Usar Glide para cargar la imagen desde la URL
        Glide.with(holder.itemView.context).load(recurso.imagen).into(holder.imagen)
    }

    override fun getItemCount(): Int = recursosFiltrados.size

    // Método para filtrar la lista de recursos
    fun filter(query: String?) {
        recursosFiltrados = if (query.isNullOrEmpty()) {
            recursos  // Si no hay consulta, mostrar todos los recursos
        } else {
            val lowerCaseQuery = query.lowercase(Locale.getDefault())
            recursos.filter {
                it.name.lowercase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        it.description.lowercase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        it.tipo.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
            }
        }
        notifyDataSetChanged()  // Notificar que los datos han cambiado para actualizar la vista
    }
}
