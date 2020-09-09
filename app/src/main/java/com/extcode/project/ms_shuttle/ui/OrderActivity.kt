package com.extcode.project.ms_shuttle.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.entity.DataOrder
import com.extcode.project.ms_shuttle.entity.DataReservation
import com.extcode.project.ms_shuttle.ui.PaymentActivity.Companion.EXTRA_DATA_ORDER
import com.extcode.project.ms_shuttle.ui.PaymentActivity.Companion.EXTRA_DATA_RESERVATION_2
import com.extcode.project.ms_shuttle.ui.fragment.BottomSheetFragment
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity(),
    BottomSheetFragment.PassDataPassenger,
    View.OnClickListener {

    companion object {
        const val EXTRA_DATA_RESERVATION = "data_reservation"
    }

    private var countAdd: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        fabAddPassenger.setOnClickListener(this)
        clearPassengerResult.setOnClickListener(this)
        toPaymentActivity.setOnClickListener(this)

    }

    override fun showDataPassenger(
        passengerName: String,
        passengerSeat: String,
        totalPassenger: Int
    ) {
        passengerPlaceHolder.visibility = View.GONE
        passengerResult.visibility = View.VISIBLE
        tvPassengerResult.visibility = View.VISIBLE
        countAdd += totalPassenger
        val result = "\n$passengerName\n$passengerSeat\n"
        val tvResult = "\nName\nSeat\n"
        tvPassengerResult.append(tvResult)
        passengerResult.append(result)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabAddPassenger -> {
                val addPhotoBottomDialogFragment: BottomSheetFragment? =
                    BottomSheetFragment().newInstance()
                addPhotoBottomDialogFragment?.show(supportFragmentManager, "BottomSheet")
            }
            R.id.clearPassengerResult -> {
                passengerResult.text = ""
                tvPassengerResult.text = ""
                countAdd = 0
                passengerPlaceHolder.visibility = View.VISIBLE
                passengerResult.visibility = View.GONE
                tvPassengerResult.visibility = View.GONE
            }
            R.id.toPaymentActivity -> {

                when {
                    buyerName.editText?.text.isNullOrEmpty() -> buyerName.error =
                        getString(R.string.please_fill_the_name)
                    buyerPhone.editText?.text.isNullOrEmpty() -> buyerPhone.error =
                        getString(R.string.please_fill_waNumber)
                    passengerResult.text.isNullOrEmpty() -> Toast.makeText(
                        this,
                        getString(R.string.please_fill_passenger),
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> {

                        Log.d("tag", "intent with unchecked")
                        buyerName.isErrorEnabled = false
                        buyerPhone.isErrorEnabled = false
                        buyerMember.isErrorEnabled = false

                        val getBuyerName = buyerName.editText?.text.toString()
                        val getBuyerPhone = buyerPhone.editText?.text.toString()
                        val getBuyerMember = buyerMember.editText?.text.toString()
                        val getAllPassenger = passengerResult.text.toString()
                        val getTvAllPassenger = tvPassengerResult.text.toString()
                        val getExtraLuggage = checkboxLuggage.isChecked
                        val getBuyerInclude = checkboxInclude.isChecked

                        val dataOrder =
                            DataOrder(
                                getBuyerName,
                                getBuyerPhone,
                                getBuyerMember,
                                getAllPassenger,
                                getTvAllPassenger,
                                getExtraLuggage,
                                getBuyerInclude,
                                countAdd
                            )

                        val dataReservation =
                            intent.getParcelableExtra<DataReservation>(EXTRA_DATA_RESERVATION)

                        val intent = Intent(this, PaymentActivity::class.java)
                        intent.putExtra(EXTRA_DATA_ORDER, dataOrder)
                        intent.putExtra(EXTRA_DATA_RESERVATION_2, dataReservation)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
                    }
                }
            }
        }
    }
}