package com.example.frauddetection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class PaymentSuccessful : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_successful)

        val imageView = findViewById<ImageView>(R.id.imageview)
        Glide.with(this).load(R.drawable.success).into(imageView)
    }
}