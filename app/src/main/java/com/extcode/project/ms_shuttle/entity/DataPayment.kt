package com.extcode.project.ms_shuttle.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataPayment(
    var total: Double = 0.0,
    var paymentMethod: String? = null
) : Parcelable