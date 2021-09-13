package com.example.weatherapp.model

enum class URL(private val s: String) {

    POSTAL("https://api.postalpincode.in/pincode/%s"),
    WEATHER("https://api.weatherapi.com/v1/current.json?key=35c9f92ac5bf4df0811144140212307&q=%s&aqi=no");

    override fun toString(): String {
        return s
    }
}