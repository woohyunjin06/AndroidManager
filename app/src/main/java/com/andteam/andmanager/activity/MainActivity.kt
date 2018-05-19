package com.andteam.andmanager.activity


import android.Manifest
import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import com.andteam.andmanager.R
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.android.synthetic.main.appbar_main.*
import org.jetbrains.anko.toast
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import com.andteam.andmanager.fragment.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.jetbrains.anko.startActivity


class  MainActivity : AppCompatActivity(){

    private val dashboardFragment = DashboardFragment()
    private val backupFragment = BackupFragment()
    private val settingFragment = SettingFragment()

    private var mFragmentManager : FragmentManager ?= null
    private var mFragmentTransaction : FragmentTransaction ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initFragment()
        nav_view.getHeaderView(0).textView.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }

    private fun initToolbar() {
        val toggle =
                ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(toolbar)
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_dashboard -> replaceFragment(dashboardFragment)
                R.id.nav_backup -> replaceFragment(backupFragment)
                R.id.nav_settings -> replaceFragment(settingFragment)
                R.id.nav_share -> toast("Share")
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

    @SuppressLint("CommitTransaction")
    private fun initFragment() {
        mFragmentManager = supportFragmentManager
        mFragmentTransaction = mFragmentManager!!.beginTransaction()
        mFragmentTransaction!!.add(R.id.fragment_container, dashboardFragment)
        mFragmentTransaction!!.commit()
        nav_view.setCheckedItem(R.id.nav_dashboard)
    }

    @SuppressLint("CommitTransaction")
    private fun replaceFragment(fragment : Fragment) {
        if(mFragmentTransaction != null) {
            mFragmentTransaction = mFragmentManager!!.beginTransaction()
            mFragmentTransaction!!.replace(R.id.fragment_container, fragment)
            mFragmentTransaction!!.commit()
        }
    }


}
