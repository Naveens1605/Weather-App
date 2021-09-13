package com.example.weatherapp.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherBinding
import com.example.weatherapp.interfaces.WeatherFetch
import com.example.weatherapp.model.URL
import com.example.weatherapp.utils.Variables
import com.example.weatherapp.viewmodel.WeatherViewModel

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentWeatherBinding
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_weather, container, false)
        setListener()
        return binding.root
    }

    private fun setListener() {
        binding.results.setOnClickListener{
            binding.progressCircular.visibility = View.VISIBLE
            if(TextUtils.isEmpty(binding.city.text)){
                Toast.makeText(Variables.context,"Please Enter City", Toast.LENGTH_LONG).show()
                binding.progressCircular.visibility = View.GONE
            }
            else{
                val city = binding.city.text.toString().replace(" ","%20")
                val url = String.format(URL.WEATHER.toString(),city)
                viewModel.getWeather(url,object : WeatherFetch {
                    override fun onFetched() {
                        binding.viewModel = viewModel
                        binding.progressCircular.visibility = View.GONE
                    }
                })
            }
        }
    }
}