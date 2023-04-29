package com.example.frauddetection

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.frauddetection.databinding.ActivitySetAppCodeBinding
import com.example.frauddetection.utils.APP_CODE
import com.example.frauddetection.utils.toast

class SetAppCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetAppCodeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetAppCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var sharedPreferences = getSharedPreferences(com.example.frauddetection.utils.storage, Context.MODE_PRIVATE)


        //if user had set appcode then force him to enter appcode
        if(sharedPreferences.contains(APP_CODE)){
            Intent(this@SetAppCodeActivity, EnterAppCode::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(this)
            }
        }

        binding.apply {

            Glide.with(this@SetAppCodeActivity).load(R.drawable.login_2).into(binding.image)

            btnSetCode.setOnClickListener {

                val appcode = etAppCode.text.toString()

                if(appcode.isBlank() || appcode.isEmpty()){
                    this@SetAppCodeActivity.toast("Please Enter Code First")
                }else if(appcode.length != 6){
                    this@SetAppCodeActivity.toast("Code should be of six digits")
                }else{
                    sharedPreferences.edit().apply{
                        putString(APP_CODE, appcode)
                        commit()
                    }
                    this@SetAppCodeActivity.toast("Code Set Successfully!!")
                    Intent(this@SetAppCodeActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(this)
                    }
                }
            }

        }




    }
}