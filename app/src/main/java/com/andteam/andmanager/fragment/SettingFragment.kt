package com.andteam.andmanager.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andteam.andmanager.R

/**
* Created by hyunjin on 2018. 5. 16..
*/

class SettingFragment : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val li : View = inflater.inflate(R.layout.fragment_setting, container, false)



        return li
    }
}