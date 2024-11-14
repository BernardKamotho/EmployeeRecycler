package com.example.employeeapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to the views
        val imageLeft = findViewById<ImageView>(R.id.imageLeft)
        val imageRight = findViewById<ImageView>(R.id.imageRight)
        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val signature = findViewById<TextView>(R.id.signature)


        // Animate the left image from left to center
        imageLeft.animate()
            .translationX(0f)
            .setDuration(3000)
            .setStartDelay(500)
            .start()

        // Animate the right image from right to center
        imageRight.animate()
            .translationX(0f)
            .setDuration(3000)
            .setStartDelay(500)
            .start()

        // Animate the welcome text from top to center
        welcomeText.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(2000)
            .setStartDelay(500)
            .start()


        // Animate the signature text from below to the bottom of the screen
        signature.animate()
            .translationY(0f) // Move to its original position at the bottom
            .alpha(1f)
            .setDuration(2000)
            .setStartDelay(1000) // Slight delay to sync with image animation
            .start()

        // Navigate to MainActivity after the animations
        Handler().postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000) // Total duration (animation duration + delay)
    }
}
