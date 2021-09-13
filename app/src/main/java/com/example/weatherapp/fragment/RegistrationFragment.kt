package com.example.weatherapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentRegistrationBinding
import com.example.weatherapp.interfaces.PincodeFetch
import com.example.weatherapp.model.URL
import com.example.weatherapp.utils.Variables
import com.example.weatherapp.viewmodel.WeatherViewModel

class RegistrationFragment : Fragment() {
    private lateinit var binding : FragmentRegistrationBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_registration, container, false)
        binding.pincode.addTextChangedListener (object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().trim().length == 6){
                    binding.checkButton.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().trim().length != 6){
                    binding.checkButton.isEnabled = false
                }
            }

        })
        setListener()
        return binding.root
    }

    private fun setListener() {
        binding.checkButton.setOnClickListener {
            binding.progressCircular.visibility = View.VISIBLE
            val url = String.format(URL.POSTAL.toString(), binding.pincode.text.toString())
            viewModel.getPincode(url, object : PincodeFetch {
                override fun onFetched() {
                    binding.viewModel = viewModel
                    binding.progressCircular.visibility = View.GONE
                }
            })
        }
        binding.registerButton.setOnClickListener {
            if(TextUtils.isEmpty(binding.mobileNumber.text) ||
                binding.mobileNumber.text.length < 10 ||
                binding.mobileNumber.text.length > 10 ||
                TextUtils.isEmpty(binding.fullname.text) ||
                TextUtils.isEmpty(binding.address1.text) ||
                TextUtils.isEmpty(binding.DOB.text) ||
                !binding.checkButton.isEnabled ||
                binding.radioGroup.checkedRadioButtonId == -1){
                Toast.makeText(Variables.context,"Please Fill Necessary/Correct Details",Toast.LENGTH_LONG).show()
            }
            else {
                val navController = NavHostFragment.findNavController(this)
                navController.navigate(R.id.action_registrationFragment_to_weatherFragment)
            }
        }

    }
}