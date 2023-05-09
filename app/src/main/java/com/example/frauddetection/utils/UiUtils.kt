package com.example.frauddetection.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: String, time: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, msg, time).show()
}

const val storage = "SHAREDPREF"
const val APP_CODE = "APP_CODE"
const val ISLOGIN = "TRUE"

const val CardNumber = "CardNumber"
const val CardName = "CardName"
const val CardExpirationDate = "CardExpirationDate"
const val CardCVV = "CardCVV"
const val CardUserNumber = "CardUserNumber"
const val CardValidityQuestion = "CardValidityQuestion"
const val CardValidityAnswer = "CardValidityAnswer"
/*

 Intent(this@SetAppCodeActivity, MainActivity::class.java).apply {
       startActivity(this)
 }

 */