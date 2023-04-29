package com.example.frauddetection

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.frauddetection.databinding.ActivityEnterAppCodeBinding
import com.example.frauddetection.utils.APP_CODE
import com.example.frauddetection.utils.CardUserNumber
import com.example.frauddetection.utils.storage
import com.example.frauddetection.utils.toast

class EnterAppCode : AppCompatActivity() {
    private lateinit var binding: ActivityEnterAppCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEnterAppCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        var sharedPreferences = getSharedPreferences(storage, Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()

        binding.apply {

            Glide.with(this@EnterAppCode).load(R.drawable.login_1).into(binding.image)

            supportActionBar?.hide()

            btnVerifyCode.setOnClickListener {
                val appcode = etAppCode.text.toString()

                if(appcode.isBlank() || appcode.isEmpty()){
                    this@EnterAppCode.toast("Please Enter Code First")
                }else if(appcode.length != 6){
                    this@EnterAppCode.toast("Code should be of six digits")
                }else{

                    if(sharedPreferences.getString(APP_CODE, "1") == appcode){
                        this@EnterAppCode.toast("User Verified!!")
                        Intent(this@EnterAppCode, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(this)
                        }
                    }else{
                        this@EnterAppCode.toast("App code is Invalid!!")
                    }


                }
            }


        }
    }
}