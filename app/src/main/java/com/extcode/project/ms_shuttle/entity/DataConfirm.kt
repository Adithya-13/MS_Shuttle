package com.extcode.project.ms_shuttle.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataConfirm(
    var a_bookingCode: Int? = 0,
    var b_pickUp: String? = null,
    var c_date: String? = null,
    var d_time: String? = null,
    var e_destination: String? = null,
    var f_buyerName: String? = null,
    var g_buyerPhone: String? = null,
    var h_buyerMember: String? = null,
    var i_allPassenger: String? = null,
    var j_extraLuggage: Boolean? = null,
    var k_total: Double? = 0.0,
    var l_paymentMethod: String? = null,
    var j_id: String? = null
) : Parcelable