package com.capstone.petverse

import android.app.Application
import com.google.firebase.FirebaseApp

class PetverseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
