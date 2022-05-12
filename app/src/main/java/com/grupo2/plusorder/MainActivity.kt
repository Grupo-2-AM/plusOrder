package com.grupo2.plusorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        val loginBt = findViewById<View>(R.id.loginBt) as Button

        loginBt.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
    }


}