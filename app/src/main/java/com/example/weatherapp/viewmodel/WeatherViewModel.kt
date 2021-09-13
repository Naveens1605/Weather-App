package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.interfaces.PincodeCallback
import com.example.weatherapp.interfaces.PincodeFetch
import com.example.weatherapp.interfaces.WeatherCallback
import com.example.weatherapp.interfaces.WeatherFetch
import com.example.weatherapp.repository.WeatherRepository
import org.json.JSONArray
import org.json.JSONObject

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    var district : String? = null
    var state : String? = null
    var tempC : String? = null
    var tempF : String? = null
    var lat : String? = null
    var lon : String? = null

    fun getPincode(url : String, pincodeFetch: PincodeFetch) {
        weatherRepository.getPincode(url, object : PincodeCallback{
            override fun onSuccess(response: JSONArray) {
                val array = response.optJSONObject(0)
                val postoffice = array.optJSONArray("PostOffice")
                val jsonObject = postoffice?.getJSONObject(0)
                district = jsonObject?.getString("District")
                state = jsonObject?.getString("State")
                pincodeFetch.onFetched()
            }
        })
    }

    fun getWeather(url: String, weatherFetch: WeatherFetch) {
        weatherRepository.getWeather(url,object : WeatherCallback{
            override fun onSuccess(response: JSONObject) {
                val current = response.optJSONObject("current")
                tempC = current?.getString("temp_c")
                tempF = current?.getString("temp_f")
                val location = response.optJSONObject("location")
                lat = location?.getString("lat")
                lon = location?.getString("lon")
                weatherFetch.onFetched()
            }
        })
    }
}