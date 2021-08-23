package com.example.calculatorwithfragment

import android.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragmentA = FragmentA()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentholder, fragmentA)
            transaction.commit()
        }
    }

}