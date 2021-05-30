package com.example.mapkotlin

import android.content.Context
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.LocationServices
import com.tomtom.online.sdk.map.MapFragment

class FavouritePlaces : AppCompatActivity() {

   //val object mainAct: MainActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favourite)

        val favourites = supportFragmentManager.findFragmentById(R.id.favs)



    }



}