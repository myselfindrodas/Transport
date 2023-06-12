package com.app.transportation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.transportation.data.Resource
import com.app.transportation.data.model.ClientListModel.ClientRequestModel
import com.app.transportation.data.model.InoutTimeModel.InOutTimeRequestModel
import com.app.transportation.data.model.LoginModel.LoginRequestModel
import com.app.transportation.data.model.ProfileDetailsModel.ProfileDetailsRequestModel
import com.app.transportation.data.model.VehicleModel.VehicleRequestModel
import com.app.transportation.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody


class CommonViewModel(private val mainRepository: MainRepository) : ViewModel() {


    fun login(requestBody: LoginRequestModel) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.login(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun vehiclelist(requestBody: VehicleRequestModel) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))

            try {
                emit(Resource.success(data = mainRepository.vehiclelist(requestBody)))
            } catch (e: Exception) {
                emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
            }
        }


    fun clientlist(requestBody: ClientRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.clientlist(requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun QRcodeupdate(requestBody: InOutTimeRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.QRcodeupdate(requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun takepic(
        type: RequestBody,
        inoutId: RequestBody,
        clients_id: RequestBody,
        vehicles_id: RequestBody,
        users_id: RequestBody,
        part: MultipartBody.Part
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.takepic(
                        type, inoutId, clients_id, vehicles_id,
                        users_id,
                        part
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun profileget(requestBody: ProfileDetailsRequestModel) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.profileget(requestBody)))
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun profileupdate(
        name: RequestBody,
        phone: RequestBody,
        gender: RequestBody,
        dob: RequestBody,
        user_id: RequestBody,
        new_password: RequestBody,
        current_password: RequestBody,
        part: MultipartBody.Part
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.profileupdate(
                        name,
                        phone,
                        gender,
                        dob,
                        user_id,
                        new_password,
                        current_password,
                        part
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


    fun profileimageupload(
        user_id: RequestBody,
        part: MultipartBody.Part
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))

        try {
            emit(
                Resource.success(
                    data = mainRepository.profileimageupload(
                        user_id,
                        part
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }


//    var cartListItem: MutableLiveData<Int> = MutableLiveData()
//    fun login(requestBody: LoginRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.login(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun urclist(requestBody: UrcRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.urclist(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun categoryall(requestBody: CategoryRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.categoryall(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun productList(requestBody: ProductListRequestModel,page:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getAllProduct(requestBody,page)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun productDetails(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getProductDetails(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun cartadd(requestBody: CartRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartadd(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun cartDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun cartList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.cartList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addressList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addressList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addAddress(requestBody: AddAddressRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addAddress(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun editAddress(requestBody: AddAddressRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.editAddress(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun deleteAddress(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.deleteAddress(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun primaryAddress(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.primaryAddress(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun dashboard(requestBody: DashboardRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.dashboard(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun logout() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.logout()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun forgotpassword(requestBody: ForgetPassRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.forgotpassword(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun addtoWishlist(requestBody: AddWishlistRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addtoWishlist(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun wishlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.wishlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun wishlistDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.wishlistDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//    fun notificationlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.notificationlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun addpaymentcard(requestBody: AddcardRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addpaymentcard(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun paymentcardlist() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardlist()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun paymentcardPrimary(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardPrimary(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun paymentcardDelete(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.paymentcardDelete(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun addOrderDetails(requestBody: AddOrderRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.addOrderDetails(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderSummeryDetails() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderSummeryDetails()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun myOrderList() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.myOrderList()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderDetails(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderDetails(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//    fun getSupportAndFAQ(id:String) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.getSupportAndFAQ(id)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun orderpayment(requestBody: OrderPaymentRequestModel) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.orderpayment(requestBody)
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun profileupdate(
//        name: RequestBody,
//        last_name: RequestBody,
//        phone: RequestBody,
//        gender: RequestBody,
//        birthday: RequestBody,
//        password: RequestBody,
//        old_password: RequestBody,
//        part: MultipartBody.Part) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//
//        try {
//            emit(Resource.success(data = mainRepository.profileupdate(
//                name,
//                last_name,
//                phone,
//                gender,
//                birthday,
//                password,
//                old_password,
//                part)))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//
//
//
//    fun profileget() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.profileget()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//    fun filterResponse() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(data = null))
//        try {
//            emit(
//                Resource.success(
//                    data = mainRepository.filterResponse()
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
}