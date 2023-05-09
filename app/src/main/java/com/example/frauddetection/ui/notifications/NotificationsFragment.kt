package com.example.frauddetection.ui.notifications

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.frauddetection.AddCard
import com.example.frauddetection.EnterAppCode
import com.example.frauddetection.MainActivity
import com.example.frauddetection.databinding.FragmentNotificationsBinding
import com.example.frauddetection.utils.*

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var sharedPref: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPref = requireContext().getSharedPreferences(storage, Context.MODE_PRIVATE)

        if(sharedPref.contains(CardName)){


            binding.apply {
                tvCardNumber.text = sharedPref.getString(CardNumber, "Card Number")
                tcHolderName.text = sharedPref.getString(CardName, "Holder Name")
                cardCvv.text = sharedPref.getString(CardCVV, "Card Cvv")
                tvHolderNumber.text = sharedPref.getString(CardUserNumber, "Holder Number")
                cardExpiry.text = sharedPref.getString(CardExpirationDate, "Card Expiry")
            }


        }

        binding.btnLogout.setOnClickListener {

            Intent(requireContext(), EnterAppCode::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or  Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(this)
                Toast.makeText(requireContext(), "User Logout Successfully!", Toast.LENGTH_SHORT).show()
            }

        }



        return root
    }

    override fun onStart() {
        super.onStart()

        //check card Already Added
        if(!sharedPref.contains(CardName)){
            Intent(requireContext(), AddCard::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}