package com.andteam.andmanager.activity


import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.andteam.andmanager.R
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.appbar_main.*
import org.jetbrains.anko.toast
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.View
import com.andteam.andmanager.R.id.toolbar



class  MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toggle =
                ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_dashboard -> toast("Dashboard")
                R.id.nav_backup -> toast("Backup")
                R.id.nav_share -> toast("Share")
                R.id.nav_settings -> toast("Setting")
                R.id.nav_about -> toast("About This App")
            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
        toolbar.setNavigationOnClickListener{
            if (drawer_layout.isDrawerOpen(Gravity.START)) {
                drawer_layout.closeDrawer(Gravity.START)
            } else {
                drawer_layout.openDrawer(Gravity.START)
            }
        }
    }
}
