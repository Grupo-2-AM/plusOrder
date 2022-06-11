package com.grupo2.plusorder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MainRecyclerAdapter(private val context: Context, private val categoria: List<Categoria>) :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {
    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         var nomeCategoria : TextView? = null

        init{
            nomeCategoria = itemView.findViewById(R.id.txt_category)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item,parent,false  ))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.nomeCategoria!!.text = categoria[position].nome
    }

    override fun getItemCount(): Int {
        return categoria.size
    }
}