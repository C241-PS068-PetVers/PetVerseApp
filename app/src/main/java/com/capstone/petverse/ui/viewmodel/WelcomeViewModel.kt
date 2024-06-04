package com.capstone.petverse.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.capstone.petverse.ui.activity.LoginActivity
import com.capstone.petverse.ui.activity.SignupActivity
import androidx.activity.ComponentActivity

class WelcomeViewModel : ViewModel() {
    fun navigateToSignup(activity: ComponentActivity) {
        val intent = Intent(activity, SignupActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToLogin(activity: ComponentActivity) {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }
}
