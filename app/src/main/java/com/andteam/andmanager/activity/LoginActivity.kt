package com.andteam.andmanager.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.andteam.andmanager.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)

        sign.setOnClickListener{
            doAsync {

            }
        }
    }
}