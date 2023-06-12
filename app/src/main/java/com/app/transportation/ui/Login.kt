package com.app.transportation.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.app.transportation.R
import com.app.transportation.base.BaseActivity
import com.app.transportation.data.ApiClient
import com.app.transportation.data.ApiHelper
import com.app.transportation.data.model.LoginModel.LoginRequestModel
import com.app.transportation.data.modelfactory.CommonModelFactory
import com.app.transportation.databinding.ActivityLoginBinding
import com.app.transportation.utils.Shared_Preferences
import com.app.transportation.utils.Status
import com.app.transportation.utils.Utilities
import com.app.transportation.viewmodel.CommonViewModel

class Login : BaseActivity() {
    lateinit var binding:ActivityLoginBinding
    var isPasswordVisible = false
    private lateinit var viewModel: CommonViewModel


    override fun resourceLayout(): Int {
        return R.layout.activity_login
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityLoginBinding

    }

    override fun setFunction() {

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        binding.btnLogin.setOnClickListener {

            if (binding.etUserName.text.toString().isEmpty()){
                Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show()
            }else if (binding.etPassword.text.toString().isEmpty()){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            }else {

                Login(binding.etUserName.text.toString(), binding.etPassword.text.toString())
            }
        }



        binding.pwdHideBtn.setOnClickListener {
            if (!isPasswordVisible) {
                binding.etPassword.transformationMethod = null
                binding.pwdHideBtn.setImageResource(R.drawable.eye_grey)
                isPasswordVisible = true

            } else {
                binding.etPassword.transformationMethod =
                    PasswordTransformationMethod()
                binding.pwdHideBtn.setImageResource(R.drawable.eye_white)
                isPasswordVisible = false
            }
            binding.etPassword.setSelection(binding.etPassword.length())
        }

    }


    @SuppressLint("NotConstructor")
    private fun Login(username:String, password:String){

        if (Utilities.isNetworkAvailable(this)) {
            viewModel.login(LoginRequestModel(email = username, password = password, type = "1")).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status==true){
                                val builder = AlertDialog.Builder(this)
                                builder.setMessage(resource.data.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    Shared_Preferences.setUserToken(resource.data.data.token)
                                    Shared_Preferences.setUserId(resource.data.data.user.id.toString())
                                    Shared_Preferences.setLoginStatus(true)
                                    val intent = Intent(this, Selectclient::class.java)
                                    startActivity(intent)
                                    finish()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()
                            }else{

                                val builder = AlertDialog.Builder(this)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()

                            }


                        }
                        Status.ERROR -> {
                            hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->"+ resource.data?.status)
                            if (it.message!!.contains("401",true)) {
                                val builder = AlertDialog.Builder(this)
                                builder.setMessage("Invalid Employee Id / Password")
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()
                            }else
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()


                        }

                        Status.LOADING -> {
                            showProgressDialog()
                        }

                    }

                }
            }



        }else{

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }



    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }


}