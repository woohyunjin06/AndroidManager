package com.andteam.andmanager.activity


import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import com.andteam.andmanager.R

import kotlinx.android.synthetic.main.appbar_main.*

class  MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

    }
}
