package com.example.delilvery2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start the LoginActivity instead of displaying the Composable screen
        startActivity(Intent(this, LoginActivity::class.java))

        // Close the MainActivity since it is no longer needed
        finish()
    }
}
