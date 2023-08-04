package com.example.devtest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.devtest.R
import com.example.devtest.databinding.ActivityMainBinding
import com.example.devtest.ui.fragments.Fragment1

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment1 = Fragment1()
        supportFragmentManager.beginTransaction().replace(R.id.fragment1,fragment1).commit()
    }
}