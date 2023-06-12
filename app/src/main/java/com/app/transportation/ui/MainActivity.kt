package com.app.transportation.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.app.transportation.R
import com.app.transportation.base.BaseActivity
import com.app.transportation.databinding.ActivityMainBinding

class MainActivity : BaseActivity(),
    NavController.OnDestinationChangedListener  {
    lateinit var binding:ActivityMainBinding
    var mNavController: NavController? = null
    var selectedclientId:String = ""
    var selectedclientName:String = ""


    override fun resourceLayout(): Int {
        return R.layout.activity_main
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityMainBinding

    }

    override fun setFunction() {
        val intent = intent
        selectedclientId = intent.getStringExtra("clientId").toString()
        selectedclientName = intent.getStringExtra("clientName").toString()

        mNavController = findNavController(R.id.flFragment)
        mNavController?.addOnDestinationChangedListener(this)
        mNavController?.navigate(R.id.nav_home)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        hideKeyboard()

    }


    override fun onDestroy() {
        findNavController(R.id.flFragment).removeOnDestinationChangedListener(this)
        super.onDestroy()
    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE

    }

}