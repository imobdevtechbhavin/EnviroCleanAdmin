package com.envirocleanadmin.data.response


import com.google.gson.annotations.SerializedName

data class CommunityAreaListResponse(
    @SerializedName("result")
    val result: ArrayList<Result?>,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: Boolean? = null
) {
    data class Result(
        @SerializedName("area_id")
        val areaId: Int? = null,
        @SerializedName("area_name")
        val areaName: String? = null,
        @SerializedName("area_latitude")
        val areaLatitude: String? = null,
        @SerializedName("area_longitude")
        val areaLongitude: String? = null,
        @SerializedName("area_range")
        val areaRange: String? = null
    )
}