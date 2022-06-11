package com.grupo2.plusorder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView

class Ementa : AppCompatActivity() {

    private var mainCategoryRecycler :RecyclerView? = null
    private var MainRecyclerAdapter : MainRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_ementa)


        val allCategoria: MutableList<Categoria> = ArrayList()
        allCategoria.add(Categoria("Boas"))
        allCategoria.add(Categoria("Boas3"))
        allCategoria.add(Categoria("Boas2"))

        setMainCategoryRecycler(allCategoria)

    }

    private fun setMainCategoryRecycler(allCategoria: List<Categoria>){

        mainCategoryRecycler = findViewById(R.id.CategoriaRecycler)
        val layoutManager:  RecyclerView.LayoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        mainCategoryRecycler!!.layoutManager = layoutManager
        MainRecyclerAdapter = MainRecyclerAdapter(this, allCategoria)
        mainCategoryRecycler!!.adapter = MainRecyclerAdapter
    }
}