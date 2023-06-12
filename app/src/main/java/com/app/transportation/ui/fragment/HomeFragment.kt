package com.app.transportation.ui.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.app.transportation.R
import com.app.transportation.data.ApiClient
import com.app.transportation.data.ApiHelper
import com.app.transportation.data.model.VehicleModel.VehicleRequestModel
import com.app.transportation.data.model.VehicleModel.VehicleResponseModel.Vehical
import com.app.transportation.data.modelfactory.CommonModelFactory
import com.app.transportation.databinding.FragmentHomeBinding
import com.app.transportation.ui.Login
import com.app.transportation.ui.MainActivity
import com.app.transportation.ui.adapter.VechicleAdapter
import com.app.transportation.utils.Shared_Preferences
import com.app.transportation.utils.Status
import com.app.transportation.utils.Utilities
import com.app.transportation.viewmodel.CommonViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(),VechicleAdapter.OnItemClickListener {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity
    private var vechicleAdapter: VechicleAdapter? = null
    private lateinit var viewModel: CommonViewModel
    var myCalendar = Calendar.getInstance()
    var selectdate: String? = ""
    var dialog: Dialog? = null
    var baseurl=""


//    private val vechicleModelArrayList: ArrayList<VechileModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root = fragmentHomeBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentHomeBinding.topnav.tvClientName.text = "Client Name: "+Shared_Preferences.getSelectedclinetname()

        val selectdate =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                dateupdateLabel(myCalendar)
            }



        fragmentHomeBinding.btnDate.setOnClickListener {

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

        fragmentHomeBinding.topnav.ivLogout.setOnClickListener {

            val builder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
            builder.setMessage("Do you want to logout?")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                Shared_Preferences.setLoginStatus(false)
                Shared_Preferences.setSelectedclinetid("")
                Shared_Preferences.setSelectedclinetname("")
                Shared_Preferences.clearPref()
                val intent = Intent(mainActivity, Login::class.java)
                startActivity(intent)
                mainActivity.finish()
                dialog.cancel()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                alert.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
            }
            alert.show()





        }

        fragmentHomeBinding.btnScan.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_camera)
        }

        vechicleAdapter = VechicleAdapter(mainActivity, this@HomeFragment)
        fragmentHomeBinding.rvVechiclelist.adapter = vechicleAdapter
        fragmentHomeBinding.rvVechiclelist.layoutManager = GridLayoutManager(mainActivity, 1)

        vehicleList("")

    }


    private fun dateupdateLabel(calendar: Calendar) {
        val selecteddateformat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(selecteddateformat, Locale.US)
        selectdate = sdf.format(calendar.time)
        fragmentHomeBinding.btnDate.setText(selectdate)
        vehicleList(selectdate.toString())

    }



    private fun vehicleList(selectedDate:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.vehiclelist(VehicleRequestModel(id = Shared_Preferences.getSelectedclinetid(), fromDate = selectedDate))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {


                                        vechicleAdapter?.updateData(itResponse.data.vehicalList)
                                        baseurl = itResponse.data.url

                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()



                                    } else {

                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Log.d(ContentValues.TAG, "print-->"+ it.message)
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



    override fun onClick(
        position: Int,
        view: View,
        mVechicleModelArrayList: ArrayList<Vehical>
    ) {


        var imgIntime: ImageView? = null
        var imgOuttime: ImageView? = null
        var btnOk: AppCompatButton? = null
        dialog = Dialog(mainActivity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val params = WindowManager.LayoutParams()
        dialog?.setContentView(R.layout.alert_image)
        dialog?.setCancelable(true)
        params.copyFrom(dialog?.getWindow()?.getAttributes())
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.gravity = Gravity.CENTER
        dialog?.getWindow()?.setAttributes(params)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
        imgIntime = dialog!!.findViewById(R.id.imgIntime)
        imgOuttime = dialog!!.findViewById(R.id.imgOuttime)
        btnOk = dialog!!.findViewById(R.id.btnOk)


        Picasso.get()
            .load(baseurl+"/"+mVechicleModelArrayList[position].inTimeImg)
            .error(R.drawable.noimage)
            .placeholder(R.drawable.loader)
            .into(imgIntime)


        Picasso.get()
            .load(baseurl+"/"+mVechicleModelArrayList[position].outTimeImg)
            .error(R.drawable.noimage)
            .placeholder(R.drawable.loader)
            .into(imgOuttime)

        btnOk?.setOnClickListener {

            dialog?.dismiss()

        }


    }





}