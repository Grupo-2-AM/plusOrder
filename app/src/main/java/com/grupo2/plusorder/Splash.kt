package com.grupo2.plusorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment


import kotlinx.coroutines.*

class Splash : AppCompatActivity() {

    private val activityScope   = CoroutineScope(Dispatchers.Main)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sploshj)

        val intent = Intent(this,  Ementa::class.java)

        val rotation = AnimationUtils.loadAnimation(this, R.anim.rotation)
        val logo : ImageView = findViewById(R.id.imageViewLogo_Splash)
        logo.startAnimation(rotation)

        //esconde barra de açoes do android
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        activityScope.launch {
            delay(3000)
            startActivity(intent)
            finish()
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