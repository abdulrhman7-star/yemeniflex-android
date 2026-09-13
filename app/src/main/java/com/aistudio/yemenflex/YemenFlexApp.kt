package com.aistudio.yemenflex

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class YemenFlexApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        val options = FirebaseOptions.Builder()
            .setProjectId("studied-archway-6ms1d")
            .setApplicationId("1:662903717813:web:1fe36e1e3adb41634a63fc")
            .setApiKey("AIzaSyB-fYAl8YIMNKWEBO4G9n7nBJEbB5o-Jhc")
            .setDatabaseUrl("https://studied-archway-6ms1d.firebaseio.com")
            .setStorageBucket("studied-archway-6ms1d.firebasestorage.app")
            .build()
            
        FirebaseApp.initializeApp(this, options)
    }
}
