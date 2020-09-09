package com.extcode.project.ms_shuttle.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataReservation(
    var pickUp: String? = null,
    var date: String? = null,
    var time: String? = null,
    var destination: String? = null,
    var orderCode: Int = 0
) : Parcelable