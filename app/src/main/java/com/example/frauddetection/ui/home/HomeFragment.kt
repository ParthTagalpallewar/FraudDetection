package com.example.frauddetection.ui.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.frauddetection.PaymentActivity2
import com.example.frauddetection.PaymentSuccessful
import com.example.frauddetection.R
import com.example.frauddetection.databinding.FragmentHomeBinding
import com.example.frauddetection.utils.*
import com.google.android.gms.location.LocationServices
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences(storage, Context.MODE_PRIVATE)


        binding.apply {
            Glide.with(this@HomeFragment).load(R.drawable.home).into(binding.image)
            selectContact.setOnClickListener {
                val pickContact = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE)
                startActivityForResult(pickContact, 1);
            }

            binding.btnMakePayment.setOnClickListener {

                val number = binding.receiveNumver.text.toString()

                if(number == "1234567890"){
                    Toast.makeText(requireContext(), "Please Select Contact Number", Toast.LENGTH_SHORT).show();
                }else if(number[0] == '+' || number.isDigitsOnly() ){
                    Intent(requireContext(), PaymentActivity2::class.java).apply {
                        startActivity(this)
                    }
                }else{
                    Toast.makeText(requireContext(), "Please Select Contact Number", Toast.LENGTH_SHORT).show();
                }



            }



        }


        return binding.root
    }



    override fun onStart() {
        super.onStart()

        if(!sharedPreferences.contains(CardName)){
            Navigation.findNavController(requireView()).navigate(R.id.action_navigation_home_to_navigation_notifications);
        }


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){

            val data: Uri? = data?.data

            if(data == null){
                t("Uri Data received is null")
            }else{
                val queryFields = arrayOf(ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)

                val cursor: Cursor = requireActivity().contentResolver.query(data, queryFields, null, null, null)!!

                if(cursor.moveToFirst()){


                    val name = cursor.getString(0)
                    val number = cursor.getString(1)

                    binding.receiveName.text = name.toString()
                    binding.receiveNumver.text = number.toString()


                }else{
                    t("content not move to first")
                }

            }



        }else{
            t("Result Code is Not ok")
        }



    }

    fun t(str: String){
        requireContext().toast(str)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}