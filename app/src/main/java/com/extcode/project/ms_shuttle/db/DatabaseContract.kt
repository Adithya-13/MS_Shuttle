package com.extcode.project.ms_shuttle.db

import android.provider.BaseColumns

object DatabaseContract {

    class ListOrderColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "listOrderTable"
            const val BOOKING_CODE = "bookingCode"
            const val PICK_UP = "pickUp"
            const val DATE = "date"
            const val TIME = "time"
            const val DESTINATION = "destination"
            const val BUYER_NAME = "buyerName"
            const val BUYER_PHONE = "buyerPhone"
            const val BUYER_MEMBER = "buyerMember"
            const val ALL_PASSENGER = "allPassenger"
            const val EXTRA_LUGGAGE = "extraLuggage"
            const val TOTAL = "total"
            const val PAYMENT_METHOD = "paymentMethod"
            const val ID = "id"
            const val TV_ALL_PASSENGER = "tvAllPassenger"
        }
    }

}