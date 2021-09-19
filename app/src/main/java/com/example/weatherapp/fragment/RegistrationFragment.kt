package com.example.weatherapp.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentRegistrationBinding
import com.example.weatherapp.interfaces.PincodeFetch
import com.example.weatherapp.model.URL
import com.example.weatherapp.viewmodel.WeatherViewModel
import java.util.*

class RegistrationFragment : Fragment() {
    private lateinit var binding : FragmentRegistrationBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weatherViewModel = ViewModelProvider(requireActivity()).get(WeatherViewModel::class.java)
        (activity as MainActivity).setActionBarTitle(resources.getString(R.string.register))
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
        setDropDown()
        setListener()
        return binding.root
    }

    private fun setListener() {
        binding.run {

            dob.showSoftInputOnFocus = false

            dob.setOnClickListener {
                val calendar = Calendar.getInstance()
                calendarPicker(calendar)
            }

            checkButton.setOnClickListener {
                progressCircular.visibility = View.VISIBLE
                val url = String.format(URL.POSTAL.toString(), pincode.text.toString())
                weatherViewModel.getPincode(url, object : PincodeFetch {
                    override fun onFetched() {
                        viewModel = weatherViewModel
                        progressCircular.visibility = View.GONE
                    }
                })
            }

            registerButton.setOnClickListener {
                if (TextUtils.isEmpty(mobileNumber.text) ||
                    mobileNumber.text?.length != 10 ||
                    TextUtils.isEmpty(fullName.text) ||
                    TextUtils.isEmpty(address1.text) ||
                    TextUtils.isEmpty(dob.text) ||
                    TextUtils.isEmpty(autoCompleteEditTex.text) ||
                    TextUtils.isEmpty(district.text)
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Please Fill Necessary/Correct Details",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val navController = NavHostFragment.findNavController(this@RegistrationFragment)
                    navController.navigate(R.id.action_registrationFragment_to_weatherFragment)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calendarPicker(calendar: Calendar) {
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        activity?.let {
            DatePickerDialog(it, { _, year, month, date ->
                binding.dob.setText("$date/${month+1}/$year")
            },year,month,date).show()
        }
    }

    private fun setDropDown() {
        val items = listOf("Male", "Female", "Others")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.gender.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}