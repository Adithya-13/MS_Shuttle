package com.extcode.project.ms_shuttle.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.db.ListOrderDatabase
import com.extcode.project.ms_shuttle.entity.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_confirmation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class ConfirmationActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_DATA_RESERVATION_FINAL = "data_reservation_final"
        const val EXTRA_DATA_ORDER_FINAL = "data_order_final"
        const val EXTRA_DATA_PAYMENT_FINAL = "data_payment_final"
    }

    private val formatter = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        applyDataReservation()
        applyDataOrder()
        applyDataPayment()

        saveAndSend.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.saveAndSend -> {

                val db = ListOrderDatabase.getInstance(this)

                val dataReservation =
                    intent.getParcelableExtra<DataReservation>(EXTRA_DATA_RESERVATION_FINAL)
                val dataOrder = intent.getParcelableExtra<DataOrder>(EXTRA_DATA_ORDER_FINAL)
                val dataPayment = intent.getParcelableExtra<DataPayment>(EXTRA_DATA_PAYMENT_FINAL)
                val sdf = SimpleDateFormat("dd-M-yyyy/hh:mm:ss", Locale.getDefault())
                val currentDate = sdf.format(Date())
                val id = "$currentDate - ${dataReservation?.orderCode}"

                val dataConfirm =
                    DataConfirm(
                        dataReservation?.orderCode,
                        dataReservation?.pickUp,
                        dataReservation?.date,
                        dataReservation?.time,
                        dataReservation?.destination,
                        dataOrder?.buyerName,
                        dataOrder?.buyerPhone,
                        dataOrder?.buyerMember,
                        dataOrder?.allPassenger,
                        dataOrder?.extraLuggage,
                        dataPayment?.total,
                        dataPayment?.paymentMethod,
                        id
                    )

                val listOrder = ListOrder(
                    dataReservation?.orderCode,
                    dataReservation?.pickUp,
                    dataReservation?.date,
                    dataReservation?.time,
                    dataReservation?.destination,
                    dataOrder?.buyerName,
                    dataOrder?.buyerPhone,
                    dataOrder?.buyerMember,
                    dataOrder?.allPassenger,
                    dataOrder?.extraLuggage,
                    dataPayment?.total,
                    dataPayment?.paymentMethod,
                    id,
                    dataOrder?.tvAllPassenger
                )

                try {
                    val firebase = FirebaseDatabase.getInstance()
                    val reference = firebase.getReference("users")

                    reference.child(id).setValue(dataConfirm)

                    GlobalScope.launch(Dispatchers.IO){
                        db.listOrderDao().insertUser(listOrder)
                    }

                    startActivity(Intent(this, ListOrderActivity::class.java))
                    overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
                    finish()

                    Toast.makeText(this, getString(R.string.booking_successful), Toast.LENGTH_SHORT).show()
                }catch (e: Exception){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun applyDataReservation() {
        val dataReservation = intent.getParcelableExtra<DataReservation>(
            EXTRA_DATA_RESERVATION_FINAL
        )
        pickUp.text = dataReservation?.pickUp
        date.text = dataReservation?.date
        time.text = dataReservation?.time
        destination.text = dataReservation?.destination
        val randomNumber = String.format(Locale.getDefault(), "#%04d", dataReservation?.orderCode)
        orderCode.text = randomNumber
    }

    private fun applyDataOrder() {
        val dataOrder = intent.getParcelableExtra<DataOrder>(EXTRA_DATA_ORDER_FINAL)
        buyerNameValue.text = dataOrder?.buyerName
        buyerPhoneValue.text = dataOrder?.buyerPhone
        buyerMemberValue.text = dataOrder?.buyerMember
        tvPassengerName.text = dataOrder?.tvAllPassenger
        passengerNameValue.text = dataOrder?.allPassenger
        luggage.text = if (dataOrder?.extraLuggage == true) "Yes" else "No"
    }

    private fun applyDataPayment() {
        val dataPayment = intent.getParcelableExtra<DataPayment>(EXTRA_DATA_PAYMENT_FINAL)
        totalValue.text = getString(R.string.rp_s, formatter.format(dataPayment?.total))
        paymentMethod.text = dataPayment?.paymentMethod
        if (dataPayment?.paymentMethod == "Bank") {
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