package com.app.transportation.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.app.transportation.R
import com.app.transportation.data.ApiClient
import com.app.transportation.data.ApiHelper
import com.app.transportation.data.modelfactory.CommonModelFactory
import com.app.transportation.databinding.FragmentVerifiedBinding
import com.app.transportation.ui.MainActivity
import com.app.transportation.utils.GetRealPathFromUri
import com.app.transportation.utils.Status
import com.app.transportation.utils.Utilities
import com.app.transportation.viewmodel.CommonViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class VerifiedFragment : Fragment() {

    lateinit var fragmentVerifiedBinding: FragmentVerifiedBinding
    lateinit var mainActivity: MainActivity
    var userId = ""
    var vehiclesid = ""
    var clientsid = ""
    var inoutid = ""
    var type = ""
    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentVerifiedBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_verified, container, false)
        val root = fragmentVerifiedBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val intent = arguments
        if (intent != null && intent.containsKey("userId")) {
            userId = intent.getString("userId", "")
        }

        if (intent != null && intent.containsKey("vehiclesid")) {
            vehiclesid = intent.getString("vehiclesid", "")
        }


        if (intent != null && intent.containsKey("clientsid")) {
            clientsid = intent.getString("clientsid", "")
        }


        if (intent != null && intent.containsKey("inoutid")) {
            inoutid = intent.getString("inoutid", "")
        }


        if (intent != null && intent.containsKey("type")) {
            type = intent.getString("type", "")
        }


        Log.d(
            TAG,
            "values-->" + userId + "," + vehiclesid + "," + clientsid + "," + inoutid + "," + type
        )


        fragmentVerifiedBinding.btnTakepic.setOnClickListener {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ImagePicker.Companion.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start()
            } else {


                if (ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    )
                } else {
                    ImagePicker.Companion.with(this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start()

                }

            }

        }


        fragmentVerifiedBinding.btnUploadinfo.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_profile)
        }
    }


    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()

        } else {
            // PERMISSION NOT GRANTED
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (fragment in childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == 2404 && resultCode == Activity.RESULT_OK) {
            val fileUri = data!!.data

            try {


                profilepicupdate(
                    GetRealPathFromUri.getPathFromUri(mainActivity, fileUri!!)!!,
                    userId,
                    vehiclesid,
                    clientsid,
                    inoutid,
                    type
                )


            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mainActivity, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mainActivity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    private fun profilepicupdate(
        fileuri: String,
        userid: String,
        vehiclesid: String,
        clientsid: String,
        inoutid: String,
        type: String
    ) {


        val file = File(fileuri)
        val fileReqBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
        val part: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, fileReqBody)
        val userid: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), userid)
        val vehiclesid: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), vehiclesid)
        val clientsid: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), clientsid)
        val inoutid: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), inoutid)
        val type: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), type)


        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.takepic(
                type,
                inoutid,
                clientsid,
                vehiclesid,
                userid, part
            )
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        Toast.makeText(
                                            mainActivity,
                                            itResponse.message,
                                            Toast.LENGTH_SHORT
                                        ).show()


                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Log.d(ContentValues.TAG, "print-->" + it.message)
//                                if (it.message!!.contains("404",true)) {
//                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()
//
//                                }else
//                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()


                            }

                            Status.LOADING -> {
                                mainActivity.showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }


    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val intent = Intent(mainActivity, MainActivity::class.java)
                    startActivity(intent)
                    mainActivity.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

}