package com.example.weatherapp.interfaces

import org.json.JSONObject

interface WeatherCallback {
    fun onSuccess(response: JSONObject)
}