package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import com.example.weatherapp.utils.Variables

class MainActivity : AppCompatActivity() {

    init {
        Variables.context = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<NavHostFragment>(R.id.navhostFragment)
            }
        }
    }

    fun setActionBarTitle(title: String?) {
        supportActionBar!!.title = title
    }
}