package com.extcode.project.ms_shuttle.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.entity.ListOrder
import kotlinx.android.synthetic.main.activity_detail_order.*
import java.text.DecimalFormat
import java.util.*

class DetailOrderActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL_ORDER = "extra_detail_order"
    }

    private val formatter = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)

        applyDataReservation()
        applyDataOrder()
        applyDataPayment()

    }


    private fun applyDataReservation() {
        val detailOrder = intent.getParcelableExtra<ListOrder>(EXTRA_DETAIL_ORDER)
        pickUp.text = detailOrder?.pickUp
        date.text = detailOrder?.date
        time.text = detailOrder?.time
        destination.text = detailOrder?.destination
        val randomNumber = String.format(Locale.getDefault(), "#%04d", detailOrder?.bookingCode)
        orderCode.text = randomNumber
    }

    private fun applyDataOrder() {
        val detailOrder = intent.getParcelableExtra<ListOrder>(EXTRA_DETAIL_ORDER)
        buyerNameValue.text = detailOrder?.buyerName
        buyerPhoneValue.text = detailOrder?.buyerPhone
        buyerMemberValue.text = detailOrder?.buyerMember
        tvPassengerName.text = detailOrder?.tvAllPassenger
        passengerNameValue.text = detailOrder?.allPassenger
        luggage.text = if (detailOrder?.extraLuggage == true) "Yes" else "No"
    }

    private fun applyDataPayment() {
        val detailOrder = intent.getParcelableExtra<ListOrder>(EXTRA_DETAIL_ORDER)
        totalValue.text = getString(R.string.rp_s, formatter.format(detailOrder?.total))
        paymentMethod.text = detailOrder?.paymentMethod
        if (detailOrder?.paymentMethod == "Bank") {
            tvNameOfBank.visibility = View.VISIBLE
            nameOfBank.visibility = View.VISIBLE
            tvAccountNumber.visibility = View.VISIBLE
            accountNumber.visibility = View.VISIBLE
            tvKindOfBank.visibility = View.VISIBLE
            kindOfBank.visibility = View.VISIBLE
        } else {
            tvNameOfBank.visibility = View.GONE
            nameOfBank.visibility = View.GONE
            tvAccountNumber.visibility = View.GONE
            accountNumber.visibility = View.GONE
            tvKindOfBank.visibility = View.GONE
            kindOfBank.visibility = View.GONE
        }
    }

}