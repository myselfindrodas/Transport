package com.app.transportation.data

import com.app.transportation.data.model.ClientListModel.ClientRequestModel
import com.app.transportation.data.model.InoutTimeModel.InOutTimeRequestModel
import com.app.transportation.data.model.LoginModel.LoginRequestModel
import com.app.transportation.data.model.ProfileDetailsModel.ProfileDetailsRequestModel
import com.app.transportation.data.model.VehicleModel.VehicleRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody


class ApiHelper(private val apiInterface: ApiInterface) {



    suspend fun login(requestBody: LoginRequestModel) = apiInterface.login(requestBody)
    suspend fun clientlist(requestBody: ClientRequestModel) = apiInterface.clientlist(requestBody)
    suspend fun vehiclelist(requestBody: VehicleRequestModel) = apiInterface.vehiclelist(requestBody)
    suspend fun QRcodeupdate(requestBody: InOutTimeRequestModel) = apiInterface.QRcodeupdate(requestBody)

    suspend fun takepic(
        type: RequestBody,
        inoutId: RequestBody,
        clients_id: RequestBody,
        vehicles_id: RequestBody,
        users_id: RequestBody,
        part: MultipartBody.Part
    ) = apiInterface.takepic(
            type,
            inoutId,
            clients_id,
            vehicles_id,
            users_id,
            part)



    suspend fun profileget(requestBody: ProfileDetailsRequestModel) = apiInterface.profileget(requestBody)


    suspend fun profileupdate(
        name: RequestBody,
        phone: RequestBody,
        gender: RequestBody,
        dob: RequestBody,
        user_id: RequestBody,
        new_password: RequestBody,
        current_password: RequestBody,
        part: MultipartBody.Part
    ) = apiInterface.profileupdate(
        name,
        phone,
        gender,
        dob,
        user_id,
        new_password,
        current_password,
        part)




    suspend fun profileimageupload(
        user_id: RequestBody,
        part: MultipartBody.Part) = apiInterface.profileimageupload(
        user_id,
        part)

//    suspend fun urclist(requestBody: UrcRequestModel) = apiInterface.urclist(requestBody)
//    suspend fun categoryall(requestBody: CategoryRequestModel) = apiInterface.categoryall(requestBody)
//    suspend fun getAllProduct(requestBody: ProductListRequestModel,page:String) = apiInterface.productList(requestBody,page)
//    suspend fun getProductDetails(id:String) = apiInterface.getProductDetails(id)
//    suspend fun dashboard(requestBody: DashboardRequestModel) = apiInterface.dashboard(requestBody)
//    suspend fun logout() = apiInterface.logout()
//    suspend fun forgotpassword(requestBody: ForgetPassRequestModel) = apiInterface.forgotpassword(requestBody)
//    suspend fun cartadd(requestBody: CartRequestModel) = apiInterface.cartadd(requestBody)
//    suspend fun cartDelete(id:String) = apiInterface.cartDelete(id)
//    suspend fun cartList() = apiInterface.cartList()
//    suspend fun addressList() = apiInterface.addressList()
//    suspend fun addAddress(requestBody: AddAddressRequestModel) = apiInterface.addAddress(requestBody)
//    suspend fun editAddress(requestBody: AddAddressRequestModel) = apiInterface.editAddress(requestBody)
//    suspend fun deleteAddress(id:String) = apiInterface.addressDelete(id)
//    suspend fun primaryAddress(id:String) = apiInterface.addressPrimary(id)
//    suspend fun addtoWishlist(requestBody: AddWishlistRequestModel) = apiInterface.addtoWishlist(requestBody)
//    suspend fun wishlist() = apiInterface.wishlist()
//    suspend fun wishlistDelete(id:String) = apiInterface.wishlistDelete(id)
//    suspend fun notificationlist() = apiInterface.notificationlist()
//    suspend fun addpaymentcard(requestBody: AddcardRequestModel) = apiInterface.addpaymentcard(requestBody)
//    suspend fun paymentcardlist() = apiInterface.paymentcardlist()
//    suspend fun paymentcardPrimary(id:String) = apiInterface.paymentcardPrimary(id)
//    suspend fun paymentcardDelete(id:String) = apiInterface.paymentcardDelete(id)
//    suspend fun addOrderDetails(requestBody: AddOrderRequestModel) = apiInterface.addOrderDetails(requestBody)
//    suspend fun orderSummeryDetails() = apiInterface.orderSummeryDetails()
//    suspend fun myOrderList() = apiInterface.myOrderList()
//    suspend fun orderDetails(id:String) = apiInterface.orderDetails(id)
//    suspend fun getSupportAndFAQ(id:String) = apiInterface.getSupportAndFAQ(id)
//    suspend fun orderpayment(requestBody: OrderPaymentRequestModel) = apiInterface.orderpayment(requestBody)
//
//    suspend fun profileupdate(
//        name: RequestBody,
//        last_name: RequestBody,
//        phone: RequestBody,
//        gender: RequestBody,
//        birthday: RequestBody,
//        password: RequestBody,
//        old_password: RequestBody,
//        part: MultipartBody.Part) = apiInterface.profileupdate(name, last_name, phone, gender, birthday, password, old_password, part)
//
//
//    suspend fun profileget() = apiInterface.profileget()
//    suspend fun filterResponse() = apiInterface.filterResponse()
}