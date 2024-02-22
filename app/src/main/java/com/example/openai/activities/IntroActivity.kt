package com.example.openai.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.example.openai.R
import com.example.openai.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private var binding: ActivityIntroBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityIntroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        binding?.image?.let {
            Glide
                .with(this)
                .load(R.drawable.ai_image)
                .centerCrop()
                .into(it)
        }
        binding?.cvChatBot?.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding?.cvImageGeneration?.setOnClickListener {
            startActivity(Intent(this,ImageActivity::class.java))
        }
    }
}
