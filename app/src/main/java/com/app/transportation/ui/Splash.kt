package com.app.transportation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.app.transportation.R
import com.app.transportation.base.BaseActivity
import com.app.transportation.databinding.ActivitySplashBinding
import com.app.transportation.utils.AnimUtils
import com.app.transportation.utils.PrefManager
import com.app.transportation.utils.Shared_Preferences

class Splash : BaseActivity() {
    lateinit var binding:ActivitySplashBinding
    private var prefManager: PrefManager? = null

    override fun resourceLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivitySplashBinding

    }

    override fun setFunction() {
        callNextScreen()
    }



    private fun callNextScreen() {

        Handler(Looper.myLooper()!!).postDelayed({

            if (Shared_Preferences.getLoginStatus()==true){

                val intent = Intent(this, Selectclient::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finish()

            }else{

                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finish()
            }

//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)
//            finish()

        }, 3000)
    }


}