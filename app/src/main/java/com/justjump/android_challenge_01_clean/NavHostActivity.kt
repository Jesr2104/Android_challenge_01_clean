package com.justjump.android_challenge_01_clean

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.justjump.android_challenge_01_clean.databinding.ActivityNavHostBinding

class NavHostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}