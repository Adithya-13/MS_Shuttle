package com.extcode.project.ms_shuttle.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.ALL_PASSENGER
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.BOOKING_CODE
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.BUYER_MEMBER
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.BUYER_NAME
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.BUYER_PHONE
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.DATE
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.DESTINATION
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.EXTRA_LUGGAGE
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.ID
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.PAYMENT_METHOD
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.PICK_UP
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.TABLE_NAME
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.TIME
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.TOTAL
import com.extcode.project.ms_shuttle.db.DatabaseContract.ListOrderColumns.Companion.TV_ALL_PASSENGER
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class ListOrder(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = BOOKING_CODE) val bookingCode: Int?,
    @ColumnInfo(name = PICK_UP) val pickUp: String?,
    @ColumnInfo(name = DATE) val date: String?,
    @ColumnInfo(name = TIME) val time: String?,
    @ColumnInfo(name = DESTINATION) val destination: String?,
    @ColumnInfo(name = BUYER_NAME) val buyerName: String?,
    @ColumnInfo(name = BUYER_PHONE) val buyerPhone: String?,
    @ColumnInfo(name = BUYER_MEMBER) val buyerMember: String?,
    @ColumnInfo(name = ALL_PASSENGER) val allPassenger: String?,
    @ColumnInfo(name = EXTRA_LUGGAGE) val extraLuggage: Boolean?,
    @ColumnInfo(name = TOTAL) val total: Double?,
    @ColumnInfo(name = PAYMENT_METHOD) val paymentMethod: String?,
    @ColumnInfo(name = ID) val id: String,
    @ColumnInfo(name = TV_ALL_PASSENGER) val tvAllPassenger: String?
): Parcelable