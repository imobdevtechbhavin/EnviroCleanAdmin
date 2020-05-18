package com.envirocleanadmin.data

import com.envirocleanadmin.base.BaseResponse
import com.envirocleanadmin.data.response.LoginResponse

import io.reactivex.Observable
import retrofit2.http.*


interface ApiService {
    // [START] Demo APIs
    /*@GET("test=123")
    fun apiGet(): Observable<BaseResponse>

    @FormUrlEncoded
    @POST("login")
    fun apiSignIn(@FieldMap params: HashMap<String, String>): Observable<BaseResponse>

    @GET("profile")
    fun apiProfile(@QueryMap params: HashMap<String, String>): Observable<BaseResponse>

    @Multipart
    @POST("update_profile")
    fun apiUpdateProfile(@PartMap params: HashMap<String, RequestBody>): Observable<BaseResponse>*/
    // [END] Demo APIs

    @Multipart
    @POST("admin/login")
    fun apiLogin(@PartMap params: HashMap<String, String>): Observable<LoginResponse>

    @Multipart
    @POST("password/email")
    fun apiForgotPassword(@PartMap params: HashMap<String, String>): Observable<BaseResponse>
}