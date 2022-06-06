package com.grupo2.plusorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginBt = findViewById<View>(R.id.loginBt) as Button

        loginBt.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        val ementaFragment = EmentaFragment()
        val qrcodeFragment = QrcodeFragment()
        val pedidosFragment = PedidosFragment()
        val perfilFragment = PerfilFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        replaceFragment(ementaFragment)

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.page_1  -> replaceFragment(ementaFragment)
                R.id.page_2  -> replaceFragment(qrcodeFragment)
                R.id.page_3  -> replaceFragment(pedidosFragment)
                R.id.page_4  -> replaceFragment(perfilFragment)
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