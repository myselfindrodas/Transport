package com.app.transportation.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.app.transportation.R
import com.app.transportation.data.ApiClient
import com.app.transportation.data.ApiHelper
import com.app.transportation.data.model.InoutTimeModel.InOutTimeRequestModel
import com.app.transportation.data.modelfactory.CommonModelFactory
import com.app.transportation.databinding.FragmentCameraBinding
import com.app.transportation.ui.MainActivity
import com.app.transportation.ui.Selectclient
import com.app.transportation.utils.Shared_Preferences
import com.app.transportation.utils.Status
import com.app.transportation.utils.Utilities
import com.app.transportation.viewmodel.CommonViewModel
import com.budiyev.android.codescanner.*
import com.google.zxing.BarcodeFormat
import java.text.SimpleDateFormat
import java.util.*

class CameraFragment : Fragment() {
    lateinit var fragmentCameraBinding: FragmentCameraBinding
    lateinit var mainActivity: MainActivity
    private lateinit var codeScanner: CodeScanner
    var status = "0"
    var uuid = ""

    //    private var imageCapture: ImageCapture? = null
//    private lateinit var outputDirectory: File
//    private lateinit var cameraExecutor: ExecutorService
    var dialog: Dialog? = null
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCameraBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        val root = fragmentCameraBinding.root
        mainActivity = activity as MainActivity
        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        codeScanner = CodeScanner(mainActivity, fragmentCameraBinding.scannerView)
        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = listOf(BarcodeFormat.QR_CODE)
        // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not


        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            mainActivity.runOnUiThread {

                uuid = it.text
                Toast.makeText(mainActivity, "Scan result Done please submit", Toast.LENGTH_LONG)
                    .show()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            mainActivity.runOnUiThread {
//                Toast.makeText(
//                    mainActivity,
//                    "Camera initialization error: ${it.message}",
//                    Toast.LENGTH_LONG
//                ).show()
            }
        }


//        outputDirectory = getOutputDirectory()
//
//        cameraExecutor = Executors.newSingleThreadExecutor()


        if (ContextCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                mainActivity,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA

                )
            )
        } else {
            codeScanner.startPreview()

        }



        fragmentCameraBinding.btnCapture.setOnClickListener {

            val sdf = SimpleDateFormat("HH:mm:ss")
            val currenttime = sdf.format(Date())
            val sdf2 = SimpleDateFormat("yyyy-dd-MM")
            val currentDate = sdf2.format(Date())

            Log.d(TAG, "Time&Date-->"+currenttime+" , "+currentDate)

            if (status.length == 0) {
                Toast.makeText(mainActivity, "Select In/Out Status", Toast.LENGTH_SHORT).show()
            } else if (uuid.length == 0) {
                Toast.makeText(mainActivity, "Scan QR code for submit", Toast.LENGTH_SHORT).show()
            } else {
                popupConfirm(
                    it, uuid, status,
                    Shared_Preferences.getSelectedclinetid(),
                    Shared_Preferences.getSelectedclinetname(),
                    Shared_Preferences.getUserId(), currenttime, currentDate
                )
            }

//            takePhoto(it)

        }


        fragmentCameraBinding.btnIn.setOnClickListener {
            fragmentCameraBinding.btnIn.setBackgroundResource(R.color.yellow)
            fragmentCameraBinding.btnOut.setBackgroundResource(R.color.white)
            status = "0"

        }



        fragmentCameraBinding.btnOut.setOnClickListener {
            fragmentCameraBinding.btnOut.setBackgroundResource(R.color.yellow)
            fragmentCameraBinding.btnIn.setBackgroundResource(R.color.white)
            status = "1"

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
            codeScanner.startPreview()

        } else {
            // PERMISSION NOT GRANTED
        }
    }


//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(mainActivity)
//
//        cameraProviderFuture.addListener({
//            // Used to bind the lifecycle of cameras to the lifecycle owner
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//
//            // Preview
//            val preview = Preview.Builder()
//                .build()
//                .also {
//                    it.setSurfaceProvider(fragmentCameraBinding.viewFinder.surfaceProvider)
//                }
//
//            imageCapture = ImageCapture.Builder()
//                .build()
//            // Select back camera as a default
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try {
//                // Unbind use cases before rebinding
//                cameraProvider.unbindAll()
//
//                // Bind use cases to camera
//                cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview, imageCapture
//                )
//
//            } catch (exc: Exception) {
//                Log.e(ContentValues.TAG, "Use case binding failed", exc)
//            }
//
//        }, ContextCompat.getMainExecutor(mainActivity))
//    }
//
//
//
//    private fun takePhoto(view: View) {
//        // Get a stable reference of the modifiable image capture use case
//        val imageCapture = imageCapture ?: return
//
//        // Create time-stamped output file to hold the image
//        val photoFile = File(
//            outputDirectory,
//            SimpleDateFormat(
//                Constants.FILENAME_FORMAT, Locale.US
//            ).format(System.currentTimeMillis()) + ".jpg"
//        )
//
//        // Create output options object which contains file + metadata
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        // Set up image capture listener, which is triggered after photo has
//        // been taken
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(mainActivity),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exc: ImageCaptureException) {
//                    Log.e(ContentValues.TAG, "Photo capture failed: ${exc.message}", exc)
//                }
//
//                @RequiresApi(Build.VERSION_CODES.O)
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val savedUri = Uri.fromFile(photoFile)
////                    picuploadToServer(GetRealPathFromUri.getPathFromUri(this@Snapshotcapture, savedUri!!)!!)
//                    val msg = "Photo capture succeeded: $savedUri"
//                    if (savedUri.toString().isNotEmpty()){
//                        popupConfirm(view)
//                    }else{
//                        Toast.makeText(mainActivity, "Capture Image First", Toast.LENGTH_SHORT).show()
//                    }
////                    Toast.makeText(mainActivity, msg, Toast.LENGTH_SHORT).show()
//                    Log.d(ContentValues.TAG, msg)
//                }
//            })
//    }
//
//
//
//    private fun getOutputDirectory(): File {
//        val mediaDir = mainActivity.externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
//        }
//        return if (mediaDir != null && mediaDir.exists())
//            mediaDir else mainActivity.filesDir
//    }


    override fun onDestroy() {
        super.onDestroy()
        codeScanner.releaseResources()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun popupConfirm(
        view: View, uuid: String,
        status: String,
        selectedclientid: String,
        selectedclientname: String,
        userid: String,
        currenttime: String,
        currentdate: String
    ) {


        var btnSubmit: AppCompatButton? = null
        var btnCancel: AppCompatButton? = null
        var tvClientname: TextView? = null
        var tvIntime: TextView? = null
        dialog = Dialog(mainActivity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val params = WindowManager.LayoutParams()
        dialog?.setContentView(R.layout.alert_confirm)
        dialog?.setCancelable(true)
        params.copyFrom(dialog?.getWindow()?.getAttributes())
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.gravity = Gravity.CENTER
        dialog?.getWindow()?.setAttributes(params)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
        btnSubmit = dialog!!.findViewById(R.id.btnSubmit)
        btnCancel = dialog!!.findViewById(R.id.btnCancel)
        tvClientname = dialog!!.findViewById(R.id.tvClientname)
        tvIntime = dialog!!.findViewById(R.id.tvIntime)

        tvClientname.setText("Client Name: " + selectedclientname)
        tvIntime.setText("Vehicle In-Time : " + currenttime)

        btnSubmit?.setOnClickListener {

            inoutTimeapi(view, uuid, status, selectedclientid, selectedclientname, userid, currenttime, currentdate)

            dialog?.dismiss()

        }


        btnCancel?.setOnClickListener {

            dialog?.dismiss()

        }

    }




    private fun inoutTimeapi(
        view: View,
        uuid: String,
        status: String,
        selectedclientid: String,
        selectedclientname: String,
        userid: String,
        currenttime: String,
        currentdate: String
    ) {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.QRcodeupdate(

                InOutTimeRequestModel(
                    status = status,
                    time = currenttime,
                    car_date = currentdate,
                    users_id = userid,
                    clients_id = selectedclientid,
                    uuid = uuid
                )
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            if (resource.data?.status == true) {
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage(resource.data.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    val bundle = Bundle()
                                    bundle.putString("userId", resource.data.data.usersId.toString())
                                    bundle.putString("vehiclesid", resource.data.data.vehiclesId.toString())
                                    bundle.putString("clientsid", resource.data.data.clientsId)
                                    bundle.putString("inoutid", resource.data.data.id.toString())
                                    bundle.putString("type", status)
                                    val navController = Navigation.findNavController(view)
                                    navController.navigate(R.id.nav_verified, bundle)

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()
                            } else {

                                val builder = AlertDialog.Builder(mainActivity)
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
                            mainActivity.hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }
}