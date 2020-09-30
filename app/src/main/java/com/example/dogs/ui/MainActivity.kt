package com.example.dogs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dogs.R
import com.example.dogs.extensions.launchActivity

class MainActivity : AppCompatActivity() {

    private val welcomeBtn : Button by lazy {
        findViewById<Button>(R.id.welcome_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeBtn.setOnClickListener {
            launchActivity<HomeActivity> {  }
        }
    }
}