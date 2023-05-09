package com.example.frauddetection

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.frauddetection.databinding.ActivityAddCardBinding
import com.example.frauddetection.databinding.ActivityPayment2Binding
import com.example.frauddetection.utils.CardName
import com.example.frauddetection.utils.CardUserNumber
import com.example.frauddetection.utils.CardValidityAnswer
import com.example.frauddetection.utils.CardValidityQuestion
import com.example.frauddetection.utils.storage
import com.example.frauddetection.utils.toast
import com.google.android.gms.location.LocationServices
import java.util.Locale

class PaymentActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityPayment2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPayment2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(storage, Context.MODE_PRIVATE)

        binding.apply {
            securityQuestion.text =
                sharedPreferences.getString(CardValidityQuestion, "Question Not Present")
            Glide.with(this@PaymentActivity2).load(R.drawable.home).into(binding.image)
            //contact number check added
            btnMakePayment.setOnClickListener {


                val answer = securityAnswer.text.toString()

                if (answer == sharedPreferences.getString(CardValidityAnswer, "No Answer Set")) {
                    Intent(this@PaymentActivity2, PaymentSuccessful::class.java).apply {
                        startActivity(this)
                    }
                } else {

                    val fusedLocationClient =
                        LocationServices.getFusedLocationProviderClient(this@PaymentActivity2)
                    if (ActivityCompat.checkSelfPermission(
                            this@PaymentActivity2,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            this@PaymentActivity2,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        t("locaton permisson not found")
                    } else {
                        fusedLocationClient.lastLocation.addOnCompleteListener {
                            if (it.result != null) {

                                val location = it.result!!

                                val geocoder = Geocoder(
                                    this@PaymentActivity2,
                                    Locale.getDefault()
                                )

                                val addresses: List<Address>? = geocoder.getFromLocation(
                                    location.latitude, location.longitude, 1
                                )

                                val address = addresses?.get(0)?.getAddressLine(0)

                                val data = "" +
                                        "Address - ${address} \n" +
                                        "Latitute - ${location.latitude} \n" +
                                        "Longitude - ${location.longitude} \n"

                                var phoneNumber: String = sharedPreferences.getString(
                                    CardUserNumber, "no"
                                )!!
                                sendSMS(phoneNumber, data)
                                sendSMS(
                                    phoneNumber,
                                    ("http://maps.google.com/maps?daddr=${location.latitude},${location.longitude}")
                                )

                                t("Sms Send")
                            } else {
                                t("Location not found")
                            }
                        }
                    }
                }
            }
        }
    }

    fun sendSMS(phoneNumber: String?, message: String?) {
        val smsManager: SmsManager = SmsManager.getDefault()
        Log.e("XXX", "sendSMS: $phoneNumber")
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }






    fun t(str: String){
        this@PaymentActivity2.toast(str)
    }

}