package com.capstone.petverse

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import com.capstone.petverse.ui.activity.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // 2 seconds delay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Use a handler to delay the navigation to the main activity
        HandlerCompat.createAsync(mainLooper).postDelayed({
            navigateToMainActivity()
        }, SPLASH_DELAY)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish() // Finish the SplashActivity to prevent going back to it
    }
}
