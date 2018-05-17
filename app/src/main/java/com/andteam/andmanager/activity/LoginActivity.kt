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



class LoginActivity : AppCompatActivity(), View.OnClickListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Toasty.Config.getInstance().apply() // Toasty Basically init

        sign.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        var result: String
        val mId = id.text.toString()
        val mPw = pw.text.toString()
        when(checkNetwork()) {
            true -> {
                when {
                    mId == "" -> Toasty.warning(this,"Please input your username").show()
                    mPw == "" -> Toasty.warning(this, "Please input your password").show()
                    else -> doAsync {
                        try {
                            val body = "id=$mId&pw=$mPw"
                            val u = URL("http://10.0.2.2:8888/login.php")
                            val huc = u.openConnection() as HttpURLConnection

                            huc.readTimeout = 4000; huc.connectTimeout = 4000
                            huc.requestMethod = "POST"
                            huc.doInput = true; huc.doOutput = true
                            huc.setRequestProperty("euc-kr", "application/x-www-form-urlencoded")

                            val os = huc.outputStream
                            os.write(body.toByteArray(charset("euc-kr")))
                            os.flush()
                            os.close()

                            val br = BufferedReader(InputStreamReader(huc.inputStream, "utf-8"), huc.contentLength)
                            val ch  = br.readLine()
                            val sb = StringBuffer()
                            sb.append(ch)
                            br.close()

                            result = sb.toString()
                            uiThread {
                                when {
                                    result.contains("SUCCESS") -> {
                                        val nick = result.replaceFirst("SUCCESS","")
                                        Toasty.success(it, "$nick SUCCESS").show()
                                    }
                                    result == "FAILED" -> {
                                        Toasty.error(it, "Please check your Username or Password.").show()
                                    }
                                    else -> {
                                        Toasty.error(it, "Please contact administrator of server.").show()
                                    }
                                }
                            }

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