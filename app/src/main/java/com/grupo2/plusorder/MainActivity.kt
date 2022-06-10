package com.grupo2.plusorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapplication.EmentaFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grupo2.plusorder.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ementaFragment = EmentaFragment()
        /* val qrcodeFragment = QrcodeFragment()
           val pedidosFragment = PedidosFragment()
           val perfilFragment = PerfilFragment() */

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        replaceFragment(ementaFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.page_1  -> replaceFragment(ementaFragment)
                /* R.id.page_2  -> replaceFragment(qrcodeFragment)
                  R.id.page_3  -> replaceFragment(pedidosFragment)
                  R.id.page_4  -> replaceFragment(perfilFragment) */
            }
            true


        }



    }

    private fun replaceFragment(fragment: Fragment)
    {
        if(fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()
        }
    }

}