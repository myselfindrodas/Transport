package com.app.transportation.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.transportation.R
import com.app.transportation.data.ApiClient
import com.app.transportation.data.ApiHelper
import com.app.transportation.data.model.ProfileDetailsModel.ProfileDetailsRequestModel
import com.app.transportation.data.modelfactory.CommonModelFactory
import com.app.transportation.databinding.FragmentProfileBinding
import com.app.transportation.ui.MainActivity
import com.app.transportation.utils.GetRealPathFromUri
import com.app.transportation.utils.Shared_Preferences
import com.app.transportation.utils.Status
import com.app.transportation.utils.Utilities
import com.app.transportation.viewmodel.CommonViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {

    lateinit var fragmentProfileBinding: FragmentProfileBinding
    lateinit var mainActivity: MainActivity
    var isPasswordVisible = false
    var myCalendar = Calendar.getInstance()
    var selectdate: String? = ""
    var selectedgender: String? = ""
    var pathFromUri: String? = ""
    private lateinit var viewModel: CommonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val root = fragmentProfileBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentProfileBinding.topnav.tvNavtitle.text = "Back"

        val selectdate =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                dateupdateLabel(myCalendar)
            }



        fragmentProfileBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        fragmentProfileBinding.imgPrf.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    mainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
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



        fragmentProfileBinding.etDOB.setOnClickListener {

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, 0)
            val datePickerDialog = DatePickerDialog(
                mainActivity, selectdate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            myCalendar=calendar

            datePickerDialog.setOnShowListener { arg0 ->
                datePickerDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue))
                datePickerDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue))
            }
//            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()

        }


        fragmentProfileBinding.spGender.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    when {
                        fragmentProfileBinding.spGender.getSelectedItem().toString() == "Male" -> selectedgender = "Male"
                        fragmentProfileBinding.spGender.getSelectedItem().toString() == "Female" -> selectedgender = "Female"
                        else -> selectedgender = ""
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        spgender()


        fragmentProfileBinding.btnPassword.setOnClickListener {

            fragmentProfileBinding.llpassword.visibility = View.GONE
            fragmentProfileBinding.llchangepassword.visibility = View.VISIBLE
        }


        fragmentProfileBinding.oldpwdHideBtn.setOnClickListener {
            if (!isPasswordVisible) {
                fragmentProfileBinding.etOldPassword.transformationMethod = null
                fragmentProfileBinding.oldpwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                isPasswordVisible = true

            } else {
                fragmentProfileBinding.etOldPassword.transformationMethod =
                    PasswordTransformationMethod()
                fragmentProfileBinding.oldpwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                isPasswordVisible = false
            }
            fragmentProfileBinding.etOldPassword.setSelection(fragmentProfileBinding.etOldPassword.length())
        }




        fragmentProfileBinding.newpwdHideBtn.setOnClickListener {
            if (!isPasswordVisible) {
                fragmentProfileBinding.etNewPassword.transformationMethod = null
                fragmentProfileBinding.newpwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                isPasswordVisible = true

            } else {
                fragmentProfileBinding.etNewPassword.transformationMethod =
                    PasswordTransformationMethod()
                fragmentProfileBinding.newpwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                isPasswordVisible = false
            }
            fragmentProfileBinding.etNewPassword.setSelection(fragmentProfileBinding.etNewPassword.length())
        }




        fragmentProfileBinding.confirmpwdHideBtn.setOnClickListener {
            if (!isPasswordVisible) {
                fragmentProfileBinding.etConfirmPassword.transformationMethod = null
                fragmentProfileBinding.confirmpwdHideBtn.setImageResource(R.drawable.ic_visibilityon)
                isPasswordVisible = true

            } else {
                fragmentProfileBinding.etConfirmPassword.transformationMethod =
                    PasswordTransformationMethod()
                fragmentProfileBinding.confirmpwdHideBtn.setImageResource(R.drawable.ic_visibilityoff)
                isPasswordVisible = false
            }
            fragmentProfileBinding.etConfirmPassword.setSelection(fragmentProfileBinding.etConfirmPassword.length())
        }



        fragmentProfileBinding.btnUpdate.setOnClickListener {


            when {
                fragmentProfileBinding.etFullname.text.toString().length==0 -> {
                    Toast.makeText(mainActivity, "Please Enter Full Name", Toast.LENGTH_SHORT).show()
                }
                fragmentProfileBinding.etPhone.text.toString().length<10 -> {
                    Toast.makeText(mainActivity, "Please Enter valid number", Toast.LENGTH_SHORT).show()
                }
                fragmentProfileBinding.etDOB.text.toString().length==0 -> {
                    Toast.makeText(mainActivity, "Please Enter DOB", Toast.LENGTH_SHORT).show()
                }
                selectedgender?.length==0 -> {
                    Toast.makeText(mainActivity, "Please Select Gender", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    when {
                        fragmentProfileBinding.llchangepassword.isVisible && fragmentProfileBinding.etOldPassword.text.toString().length > 0 -> {
                            when {
                                fragmentProfileBinding.etNewPassword.text.toString().length == 0 -> {
                                    Toast.makeText(mainActivity, "Please Enter New Password", Toast.LENGTH_SHORT).show()
                                }
                                fragmentProfileBinding.etConfirmPassword.text.toString().length == 0 -> {
                                    Toast.makeText(mainActivity, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show()
                                }
                                fragmentProfileBinding.etNewPassword.text.toString() != fragmentProfileBinding.etConfirmPassword.text.toString() -> {
                                    Toast.makeText(mainActivity, "Password not matched", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    when {
                                        pathFromUri.equals("") -> {
                                            updateprofile()
                                        }
                                        else -> {
                                            profilepicupload(pathFromUri.toString())
                                        }
                                    }

                                }
                            }

                        }
                        else -> {

                            when {
                                pathFromUri.equals("") -> {
                                    updateprofile()
                                }
                                else -> {
                                    profilepicupload(pathFromUri.toString())

                                }
                            }

                        }
                    }
                }
            }
        }


        profileget()


    }


    private fun dateupdateLabel(calendar: Calendar) {
        val selecteddateformat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(selecteddateformat, Locale.US)
        selectdate = sdf.format(calendar.time)
        fragmentProfileBinding.etDOB.setText(selectdate)
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
                Picasso.get()
                    .load(fileUri)
                    .into(fragmentProfileBinding.imgPrf)

                pathFromUri = GetRealPathFromUri.getPathFromUri(mainActivity, fileUri!!)!!
                profileimageupload(GetRealPathFromUri.getPathFromUri(mainActivity, fileUri!!)!!)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mainActivity, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mainActivity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun spgender() {
        val gender: MutableList<String> = ArrayList()
        gender.add("Select Gender")
        gender.add("Male")
        gender.add("Female")
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, gender)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentProfileBinding.spGender.adapter = arrayAdapter
    }




    private fun profileget(){

        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.profileget(ProfileDetailsRequestModel(user_id = Shared_Preferences.getUserId()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {

                                        fragmentProfileBinding.etFullname.setText(itResponse.data.user[0].name?:"")
                                        fragmentProfileBinding.etPhone.setText(itResponse.data.user[0].phone)
                                        if (itResponse.data.user[0].gender.equals("Male")){
                                            fragmentProfileBinding.spGender.setSelection(1)
                                        }else if (itResponse.data.user[0].gender.equals("Female")){
                                            fragmentProfileBinding.spGender.setSelection(2)
                                        }else{
                                            fragmentProfileBinding.spGender.setSelection(0)
                                        }
                                        fragmentProfileBinding.etDOB.setText(itResponse.data.user[0].dob?:"")


                                        Picasso.get()
                                            .load(resource.data.data.url+"/"+itResponse.data.user[0].img)
                                            .error(R.drawable.dp)
                                            .into(fragmentProfileBinding.imgPrf)

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
                                Log.d(ContentValues.TAG, "print-->"+ resource.data?.status)
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


    private fun updateprofile(){


        if (Utilities.isNetworkAvailable(mainActivity)) {

            val fileReqBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), "")
            val part: MultipartBody.Part = MultipartBody.Part.createFormData("file", "", fileReqBody)

            val name: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etFullname.text.toString())
            val phone: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etPhone.text.toString())
            val gender: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), selectedgender.toString())
            val birthday: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etDOB.text.toString())

            val userid: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), Shared_Preferences.getUserId())

            val newpassword: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etConfirmPassword.text.toString()?:"")

            val currentpassword: RequestBody =
                RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etOldPassword.text.toString()?:"")


            viewModel.profileupdate(
                name,
                phone,
                gender,
                birthday,
                userid,
                newpassword,
                currentpassword,
                part = part
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            resource.data?.let {itResponse->

                                if (itResponse.status) {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
                                    val intent = Intent(mainActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    mainActivity.finish()
                                } else {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                }
                            }


                        }
                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setMessage(it.message)
                            builder.setPositiveButton(
                                "Ok"
                            ) { dialog, which ->

                                dialog.cancel()

                            }
                            val alert = builder.create()
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.blue))
                                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setTextColor(resources.getColor(R.color.blue))
                            }
                            alert.show()
                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        }else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }


    private fun profilepicupload(pathFromUri: String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            val file = File(pathFromUri)
            val fileReqBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
            val part: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.name, fileReqBody)
            val name: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etFullname.text.toString())
            val phone: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etPhone.text.toString())
            val gender: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), selectedgender.toString())
            val birthday: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etDOB.text.toString())
            val userid: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), Shared_Preferences.getUserId())
            val newpassword: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etConfirmPassword.text.toString()?:"")

            val currentpassword: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), fragmentProfileBinding.etOldPassword.text.toString()?:"")


            viewModel.profileupdate(
                name, phone, gender, birthday, userid,
                newpassword, currentpassword, part = part).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            resource.data?.let {itResponse->
                                if (itResponse.status) {
                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
                                    val intent = Intent(mainActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    mainActivity.finish()

                                } else {
                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()
                                }
                            }


                        }
                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            val builder = AlertDialog.Builder(mainActivity)
                            builder.setMessage(it.message)
                            builder.setPositiveButton(
                                "Ok"
                            ) { dialog, which ->

                                dialog.cancel()

                            }
                            val alert = builder.create()
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.orange))
                                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                                    .setTextColor(resources.getColor(R.color.orange))
                            }
                            alert.show()
                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        }else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }

    }



    private fun profileimageupload(fileuri:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            val file = File(fileuri)
            val fileReqBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
            val part: MultipartBody.Part =
                MultipartBody.Part.createFormData("file", file.name, fileReqBody)

            val userid: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), Shared_Preferences.getUserId())


            viewModel.profileimageupload(userid,part).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            resource.data?.let {itResponse->

                                if (itResponse.status) {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                    Picasso.get()
                                        .load(resource.data.data.url+"/"+itResponse.data.imgName)
                                        .error(R.drawable.dp)
                                        .into(fragmentProfileBinding.imgPrf)

                                } else {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                }
                            }


                        }
                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->"+ it.message)
//                                if (it.message!!.contains("404",true)) {
//                                    Toast.makeText(mainActivity, "No data found", Toast.LENGTH_SHORT).show()
//                                }else
                            Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()
                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        }else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }





}