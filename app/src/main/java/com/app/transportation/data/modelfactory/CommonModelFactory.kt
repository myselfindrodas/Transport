package com.app.transportation.data.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.transportation.data.ApiHelper
import com.app.transportation.data.repository.MainRepository
import com.app.transportation.viewmodel.CommonViewModel

class CommonModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CommonViewModel(MainRepository(apiHelper)) as T
}