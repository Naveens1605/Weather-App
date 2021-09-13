package com.example.weatherapp.repository

import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.weatherapp.interfaces.PincodeCallback
import com.example.weatherapp.interfaces.WeatherCallback
import com.example.weatherapp.singleton.MySingleton
import com.example.weatherapp.utils.Variables

class WeatherRepository {

    private var mySingleton = MySingleton.getInstance(Variables.context)

    fun getPincode(url : String, pincodeCallback: PincodeCallback) {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                pincodeCallback.onSuccess(response)
            },
            { error ->
                Toast.makeText(Variables.context,"ERROR $error",Toast.LENGTH_LONG).show()
            }
        )

// Access the RequestQueue through your singleton class.
        mySingleton.addToRequestQueue(jsonArrayRequest)
    }

    fun getWeather(url: String,weatherCallback: WeatherCallback){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                weatherCallback.onSuccess(response)
            },
            { error ->
                Toast.makeText(Variables.context,"ERROR $error",Toast.LENGTH_LONG).show()
            }
        )

// Access the RequestQueue through your singleton class.
       mySingleton.addToRequestQueue(jsonObjectRequest)
    }
}