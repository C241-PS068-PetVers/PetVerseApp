package com.capstone.petverse

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.capstone.petverse.ui.activity.WelcomeActivity
import com.capstone.petverse.ui.viewmodel.UserViewModel
import com.capstone.petverse.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 2000 // 2 seconds delay
    private val viewModel: UserViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            checkSession()
        }, SPLASH_DELAY)
    }

    private fun checkSession() {
        lifecycleScope.launch {
            viewModel.getSession().collect { session ->
                if (session.isLogin) {
                    navigateToHomeActivity()
                } else {
                    navigateToWelcomeActivity()
                }
            }
        }
    }

    private fun navigateToHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish the SplashActivity to prevent going back to it
    }

    private fun navigateToWelcomeActivity() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
