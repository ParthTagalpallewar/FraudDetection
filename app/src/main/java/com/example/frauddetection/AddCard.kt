package com.example.frauddetection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.cardform.view.CardForm
import com.example.frauddetection.databinding.ActivityAddCardBinding
import com.example.frauddetection.databinding.ActivityMainBinding
import com.example.frauddetection.utils.*


class AddCard : AppCompatActivity() {

    private lateinit var binding: ActivityAddCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences(storage, Context.MODE_PRIVATE)


        supportActionBar?.title = "Add Card"

        binding.cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .cardholderName(CardForm.FIELD_REQUIRED)
            .mobileNumberRequired(true)
            .mobileNumberExplanation("SMS is required on this number")
            .actionLabel("Purchase")
            .setup(this)

        binding.btnAddCard.setOnClickListener {
            if(binding.cardForm.isValid && binding.etQuestion.text.toString().isNotEmpty() && binding.etQuestion.text.toString().isNotEmpty()){

                sharedPref.edit().apply {
                    putString(CardName, binding.cardForm.cardholderName.toString())
                    putString(CardNumber, binding.cardForm.cardNumber.toString())
                    putString(CardExpirationDate, (binding.cardForm.expirationMonth.toString()+"/"+ binding.cardForm.expirationYear.toString()))
                    putString(CardCVV, binding.cardForm.cvv.toString())
                    putString(CardUserNumber, ("+"+binding.cardForm.countryCode.toString()+ binding.cardForm.mobileNumber.toString()))
                    putString(CardValidityQuestion, binding.etQuestion.text.toString())
                    putString(CardValidityAnswer, binding.etAnswer.text.toString())
                    apply()
                }

                toast("Card Added Successfully!!")

                Intent(this@AddCard, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(this)
                }


            }else{
                this.toast("Please Enter Valid Card Details")
            }
        }

    }
}