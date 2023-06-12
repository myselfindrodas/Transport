package com.app.transportation.data.repository

import com.app.transportation.data.ApiHelper
import com.app.transportation.data.model.ClientListModel.ClientRequestModel
import com.app.transportation.data.model.InoutTimeModel.InOutTimeRequestModel
import com.app.transportation.data.model.LoginModel.LoginRequestModel
import com.app.transportation.data.model.ProfileDetailsModel.ProfileDetailsRequestModel
import com.app.transportation.data.model.VehicleModel.VehicleRequestModel
import okhttp3.MultipartBody
import okhttp3.RequestBody


class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun login(requestBody: LoginRequestModel) = apiHelper.login(requestBody)
    suspend fun clientlist(requestBody: ClientRequestModel) = apiHelper.clientlist(requestBody)
    suspend fun vehiclelist(requestBody: VehicleRequestModel) = apiHelper.vehiclelist(requestBody)
    suspend fun QRcodeupdate(requestBody: InOutTimeRequestModel) = apiHelper.QRcodeupdate(requestBody)

    suspend fun takepic(
        type: RequestBody,
        inoutId: RequestBody,
        clients_id: RequestBody,
        vehicles_id: RequestBody,
        users_id: RequestBody,
        part: MultipartBody.Part) = apiHelper.takepic(
        type,
        inoutId,
        clients_id,
        vehicles_id,
        users_id,
        part)

    suspend fun profileget(requestBody: ProfileDetailsRequestModel) = apiHelper.profileget(requestBody)


    suspend fun profileupdate(
        name: RequestBody,
        phone: RequestBody,
        gender: RequestBody,
        dob: RequestBody,
        user_id: RequestBody,
        new_password: RequestBody,
        current_password: RequestBody,
        part: MultipartBody.Part
    ) = apiHelper.profileupdate(
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
        part: MultipartBody.Part) = apiHelper.profileimageupload(
        user_id,
        part)

//    suspend fun login(devicetype: String, key: String, source: String, requestBody: LoginRequestModel) =
//        apiHelper.login(devicetype,key,source, requestBody)
//
//    suspend fun validateotp(devicetype: String, key: String, source: String, requestBody: OtpvalidateRequestModel) =
//        apiHelper.validateotp(devicetype,key,source, requestBody)
//
//
//    suspend fun logout(devicetype: String, key: String, source: String) = apiHelper.logout(devicetype,key,source)
//
//
//    suspend fun expireotp(devicetype: String, key: String, source: String, requestBody: ExpriedOtpRequestModel) =
//        apiHelper.expireotp(devicetype,key,source, requestBody)
//
//
//    suspend fun resendotp(devicetype: String, key: String, source: String, requestBody: ExpriedOtpRequestModel) =
//        apiHelper.resendotp(devicetype,key,source, requestBody)
//
//
//    suspend fun saveaddress(devicetype: String, key: String, source: String, requestBody: AddAddressRequestModel) =
//        apiHelper.saveaddress(devicetype,key,source, requestBody)
//
//
//    suspend fun addresslist(devicetype: String, key: String, source: String) = apiHelper.addresslist(devicetype,key,source)
//
//
//    suspend fun addressdetails(devicetype: String, key: String, source: String, requestBody: EditAddressRequestModel) =
//        apiHelper.addressdetails(devicetype,key,source, requestBody)
//
//
//    suspend fun updateaddress(devicetype: String, key: String, source: String, requestBody: UpdateAddressRequestModel) =
//        apiHelper.updateaddress(devicetype,key,source, requestBody)
//
//
//    suspend fun deleteaddress(devicetype: String, key: String, source: String, requestBody: DeleteAddressRequestModel) =
//        apiHelper.deleteaddress(devicetype,key,source, requestBody)
//
//
//
//    suspend fun primaryaddress(devicetype: String, key: String, source: String, requestBody: PrimaryAddressRequestModel) =
//        apiHelper.primaryaddress(devicetype,key,source, requestBody)

//    suspend fun login(requestBody: LoginRequestModel) = apiHelper.login(requestBody)
//    suspend fun urclist(requestBody: UrcRequestModel) = apiHelper.urclist(requestBody)
//    suspend fun categoryall(requestBody: CategoryRequestModel) = apiHelper.categoryall(requestBody)
//    suspend fun getAllProduct(requestBody: ProductListRequestModel,page:String) = apiHelper.getAllProduct(requestBody,page)
//    suspend fun getProductDetails(id:String) = apiHelper.getProductDetails(id)
//    suspend fun dashboard(requestBody: DashboardRequestModel) = apiHelper.dashboard(requestBody)
//    suspend fun logout() = apiHelper.logout()
//    suspend fun forgotpassword(requestBody: ForgetPassRequestModel) = apiHelper.forgotpassword(requestBody)
//    suspend fun cartadd(requestBody: CartRequestModel) = apiHelper.cartadd(requestBody)
//    suspend fun cartDelete(id:String) = apiHelper.cartDelete(id)
//    suspend fun cartList() = apiHelper.cartList()
//    suspend fun addressList() = apiHelper.addressList()
//    suspend fun addAddress(requestBody: AddAddressRequestModel) = apiHelper.addAddress(requestBody)
//    suspend fun editAddress(requestBody: AddAddressRequestModel) = apiHelper.editAddress(requestBody)
//    suspend fun deleteAddress(id:String) = apiHelper.deleteAddress(id)
//    suspend fun primaryAddress(id:String) = apiHelper.primaryAddress(id)
//    suspend fun addtoWishlist(requestBody: AddWishlistRequestModel) = apiHelper.addtoWishlist(requestBody)
//    suspend fun wishlist() = apiHelper.wishlist()
//    suspend fun wishlistDelete(id:String) = apiHelper.wishlistDelete(id)
//    suspend fun notificationlist() = apiHelper.notificationlist()
//    suspend fun addpaymentcard(requestBody: AddcardRequestModel) = apiHelper.addpaymentcard(requestBody)
//    suspend fun paymentcardlist() = apiHelper.paymentcardlist()
//    suspend fun paymentcardPrimary(id:String) = apiHelper.paymentcardPrimary(id)
//    suspend fun paymentcardDelete(id:String) = apiHelper.paymentcardDelete(id)
//    suspend fun addOrderDetails(requestBody: AddOrderRequestModel) = apiHelper.addOrderDetails(requestBody)
//    suspend fun orderSummeryDetails() = apiHelper.orderSummeryDetails()
//    suspend fun myOrderList() = apiHelper.myOrderList()
//    suspend fun orderDetails(id:String) = apiHelper.orderDetails(id)
//    suspend fun getSupportAndFAQ(id:String) = apiHelper.getSupportAndFAQ(id)
//    suspend fun orderpayment(requestBody: OrderPaymentRequestModel) = apiHelper.orderpayment(requestBody)
//
//    suspend fun profileupdate(
//        name: RequestBody,
//        last_name: RequestBody,
//        phone: RequestBody,
//        gender: RequestBody,
//        birthday: RequestBody,
//        password: RequestBody,
//        old_password: RequestBody,
//        part: MultipartBody.Part) = apiHelper.profileupdate(name, last_name, phone, gender, birthday, password, old_password, part)
//
//    suspend fun profileget() = apiHelper.profileget()
//    suspend fun filterResponse() = apiHelper.filterResponse()
}