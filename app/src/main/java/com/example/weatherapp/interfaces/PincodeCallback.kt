package com.example.weatherapp.interfaces

import org.json.JSONArray

interface PincodeCallback {
    fun onSuccess(response: JSONArray)
}