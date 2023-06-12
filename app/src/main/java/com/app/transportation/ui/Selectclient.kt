package com.app.transportation.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.app.transportation.R
import com.app.transportation.base.BaseActivity
import com.app.transportation.data.ApiClient
import com.app.transportation.data.ApiHelper
import com.app.transportation.data.model.ClientListModel.ClientRequestModel
import com.app.transportation.data.modelfactory.CommonModelFactory
import com.app.transportation.databinding.ActivitySelectclientBinding
import com.app.transportation.utils.Shared_Preferences
import com.app.transportation.utils.Utilities
import com.app.transportation.viewmodel.CommonViewModel

class Selectclient : BaseActivity() {

    lateinit var binding: ActivitySelectclientBinding
    var spinnerClientNameArray = ArrayList<String>()
    var spinnerClientIdArray = ArrayList<Int>()
    var selectedclientId = ""
    var selectedclientName = ""
    private lateinit var viewModel: CommonViewModel


    override fun resourceLayout(): Int {
        return R.layout.activity_selectclient
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivitySelectclientBinding
    }

    override fun setFunction() {

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm


        binding.btnNext.setOnClickListener {

            if (selectedclientId.equals("")){
                Toast.makeText(this, "Select Client", Toast.LENGTH_SHORT).show()

            }else{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("clientId", selectedclientId)
                intent.putExtra("clientName", selectedclientName)
                Shared_Preferences.setSelectedclinetid(selectedclientId)
                Shared_Preferences.setSelectedclinetname(selectedclientName)
                startActivity(intent)
            }

        }

        spClient()


        binding.spClient.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                selectedclientId = spinnerClientIdArray[binding.spClient.selectedItemPosition].toString()
                selectedclientName = spinnerClientNameArray[binding.spClient.selectedItemPosition]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }


    private fun spClient() {

        if (Utilities.isNetworkAvailable(this)) {

            viewModel.clientlist(ClientRequestModel(superviserId = Shared_Preferences.getUserId())).observe(this) {
                it?.let { resource ->
                    if (resource.data?.status == true) {

                        spinnerClientNameArray = ArrayList<String>()
                        spinnerClientIdArray = ArrayList<Int>()

                        resource.data.data.forEach { i ->
                            i.clientsAlloction.forEach { j ->
                                spinnerClientNameArray.add(j.name)
                                spinnerClientIdArray.add(j.id)
                            }
                        }
                        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            spinnerClientNameArray
                        )
                        binding.spClient.adapter = spinnerArrayAdapter


                    }


                }
            }

        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }
    }


//    private fun spclient() {
//        val gender: MutableList<String> = ArrayList()
//        gender.add("Select Client")
//        gender.add("John Doe")
//        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender)
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        binding.spClient.setAdapter(arrayAdapter)
//    }

}