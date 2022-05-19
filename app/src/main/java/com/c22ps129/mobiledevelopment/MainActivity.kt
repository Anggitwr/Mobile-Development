package com.c22ps129.mobiledevelopment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps129.mobiledevelopment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}