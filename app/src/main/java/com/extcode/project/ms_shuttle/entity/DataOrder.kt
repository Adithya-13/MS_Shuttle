package com.extcode.project.ms_shuttle.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataOrder(
    var buyerName: String? = null,
    var buyerPhone: String? = null,
    var buyerMember: String? = null,
    var allPassenger: String? = null,
    var tvAllPassenger: String? = null,
    var extraLuggage: Boolean? = null,
    var buyerInclude: Boolean? = null,
    var countAdd: Int = 0
) : Parcelable