package com.andteam.andmanager.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.andteam.andmanager.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import android.net.ConnectivityManager
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity
import com.google.firebase.internal.FirebaseAppHelper.getUid
import com.google.firebase.auth.FirebaseUser
import android.support.annotation.NonNull





class LoginActivity : AppCompatActivity(), View.OnClickListener{


    private var mAuth: FirebaseAuth? = null
    var mContext: Context? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }
    private fun init(){
        Toasty.Config.getInstance().apply() // Toasty init

        sign.setOnClickListener(this)
        sign_up.setOnClickListener(this)

        mContext = this
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                Toasty.info(this, "Signed in " + user.email).show()
                startActivity<MainActivity>("email" to user.email)
                finish()
            } else {
                Toasty.info(this, "Signed out").show()
            }
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.sign -> {
                val mEmail = email.text.toString()
                val mPw = pw.text.toString()
                when(checkNetwork()) {
                    true -> {
                        when {
                            mEmail == "" -> Toasty.warning(this, "Please input your email").show()
                            mPw == "" -> Toasty.warning(this, "Please input your password").show()
                            else -> loginAccount(mEmail, mPw)
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

    private fun loginAccount(email: String, password: String){
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        Toasty.success(this, "Sign in successfully").show()

                    }
                    else {
                        Toasty.error(this, "Sign in failed").show()
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

    public override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    public override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }
}