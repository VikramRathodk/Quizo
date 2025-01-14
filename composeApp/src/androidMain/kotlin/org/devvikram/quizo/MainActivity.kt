package org.devvikram.quizo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.devvikram.quizo.presentation.AppViewModel
import org.devvikram.quizo.utils.SharedPreference

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        val firebase = Firebase
        sharedPreference = SharedPreference(this)
        val appViewmodel = AppViewModel(firebase,sharedPreference)
        setContent {
            App(appViewmodel)
        }
    }
}

