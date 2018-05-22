package com.andteam.andmanager.activity

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.andteam.andmanager.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern
import org.jetbrains.anko.startActivity


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private var mAuth: FirebaseAuth? = null
    var mContext: Context? = null
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }
    private fun init(){
        Toasty.Config.getInstance().apply()
        sign.setOnClickListener(this)
        mContext = this
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                Toasty.info(this, "onAuthStateChanged:signed_in:" + user.uid).show()
            } else {
                // User is signed out
                Toasty.info(this, "onAuthStateChanged:signed_out").show()
            }
        }
    }
    override fun onClick(v: View?) {
        val mPw = pw.text.toString()
        val mPwConfirm = pw2.text.toString()
        val mEmail = email.text.toString()
        when(checkNetwork()) {
            true -> {
                when {
                    mEmail == "" -> Toasty.warning(this, "Please input your email").show()
                    mPw == "" -> Toasty.warning(this, "Please input your password").show()
                    mPwConfirm == "" -> Toasty.warning(this, "Please confirm your password").show()
                    mPw != mPwConfirm -> Toasty.warning(this, "Please verify your re-entered password").show()
                    else -> createAccount(mEmail, mPw)
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

    private fun isValidPassword(target: String): Boolean {
        val p = Pattern.compile("(^.*(?=.{6,100})(?=.*[0-9])(?=.*[a-zA-Z]).*$)")

        val m = p.matcher(target)
        return m.find() && !target.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*".toRegex())
    }

    private fun isValidEmail(target: String): Boolean{
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun createAccount(email: String, password: String) {
        if (!isValidEmail(email)) {
            Toasty.error(this,"Email is not valid ").show()
            return
        }

        if (!isValidPassword(password)) {
            Toasty.error(this,"Password is not valid").show()
            return
        }

        //start Progress

        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        Toasty.success(this,"Sign up complete successfully").show()
                        startActivity<LoginActivity>()
                        finish()
                    }
                    else {
                        Toasty.error(this, "Sign up failed").show()
                    }

                    //end Progress
                }
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
