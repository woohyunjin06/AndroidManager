package com.andteam.andmanager.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.andteam.andmanager.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.net.ConnectivityManager
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }
    private fun init(){
        Toasty.Config.getInstance().apply() // Toasty init

        sign.setOnClickListener(this)
        sign_up.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.sign -> {
                var result: String
                val mId = id.text.toString()
                val mPw = pw.text.toString()
                when(checkNetwork()) {
                    true -> {
                        when {
                            mId == "" -> Toasty.warning(this, "Please input your username").show()
                            mPw == "" -> Toasty.warning(this, "Please input your password").show()
                            else -> doAsync {
                                try {
                                    // do Something

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                    false -> {
                        Toasty.warning(this, "Please check your network.").show()
                    }
                }
            }
            R.id.sign_up -> startActivity<RegisterActivity>()
        }
    }

    private fun checkNetwork() : Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            return when {
                activeNetwork.type == ConnectivityManager.TYPE_WIFI -> true
                activeNetwork.type == ConnectivityManager.TYPE_MOBILE -> true
                else -> false
            }
        }
        return false
    }
}