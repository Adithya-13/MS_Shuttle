package com.extcode.project.ms_shuttle.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.ui.ConfirmationActivity.Companion.EXTRA_DATA_ORDER_FINAL
import com.extcode.project.ms_shuttle.ui.ConfirmationActivity.Companion.EXTRA_DATA_PAYMENT_FINAL
import com.extcode.project.ms_shuttle.ui.ConfirmationActivity.Companion.EXTRA_DATA_RESERVATION_FINAL
import com.extcode.project.ms_shuttle.entity.DataOrder
import com.extcode.project.ms_shuttle.entity.DataPayment
import com.extcode.project.ms_shuttle.entity.DataReservation
import kotlinx.android.synthetic.main.activity_payment.*
import java.text.DecimalFormat

class PaymentActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        const val EXTRA_DATA_ORDER = "data_order"
        const val EXTRA_DATA_RESERVATION_2 = "data_reservation_2"
    }

    private var nominal: Double = 0.0
    private var discount: Double = 0.0
    private var afterDiscount: Double = 0.0
    private var luggage: Double = 0.0
    private var total: Double = 0.0

    private val formatter = DecimalFormat("#,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        ArrayAdapter.createFromResource(
            this,
            R.array.payment_method,
            android.R.layout.simple_list_item_1
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            paymentMethodSpinner.getSpinner().adapter = arrayAdapter
            paymentMethodSpinner.getSpinner().onItemSelectedListener = this
        }

        total()
        toConfirmationActivity.setOnClickListener {
            when (paymentMethodSpinner.getSpinner().selectedItemPosition) {
                0 -> paymentMethodSpinner.setError(getString(R.string.please_select_payment_method))
                else -> {
                    val dataPayment =
                        DataPayment(
                            total,
                            paymentMethodSpinner.getSpinner().selectedItem.toString()
                        )

                    val dataReservation = intent.getParcelableExtra<DataReservation>(
                        EXTRA_DATA_RESERVATION_2
                    )
                    val dataOrder = intent.getParcelableExtra<DataOrder>(EXTRA_DATA_ORDER)

                    Intent(this, ConfirmationActivity::class.java).apply {
                        putExtra(EXTRA_DATA_PAYMENT_FINAL, dataPayment)
                        putExtra(EXTRA_DATA_RESERVATION_FINAL, dataReservation)
                        putExtra(EXTRA_DATA_ORDER_FINAL, dataOrder)
                        startActivity(this)
                        overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
                    }
                }
            }
        }
    }

    private fun total() {
        totalPassengerPrice()
        totalPassengerDiscount()
        totalExtraLuggage()
        total = nominal - afterDiscount + luggage
        totalPrice.text = getString(R.string.rp_s, formatter.format(total))
    }

    private fun totalPassengerPrice() {
        val getCountAdd = intent?.getParcelableExtra<DataOrder>(EXTRA_DATA_ORDER)?.countAdd
        nominal = getCountAdd?.times(100000)?.toDouble() ?: 0.0
        nominalPrice.text = getString(R.string.rp_s, formatter.format(nominal))
        subTotalPrice.text = getString(R.string.d_passenger_x_price, getCountAdd)
    }

    private fun totalPassengerDiscount() {
        val dataOrder = intent?.getParcelableExtra<DataOrder>(EXTRA_DATA_ORDER)
        val getMember = dataOrder?.buyerMember
        if (getMember != null) {
            if (getMember.isNullOrEmpty()) {
                discount = 0.0
                afterDiscount = nominal * discount
                discountPrice.text = getString(R.string.rp_s, formatter.format(afterDiscount))
                subTotalDiscount.text = getString(R.string.discount_s, "0%")
            } else {
                discount = 0.1
                afterDiscount = nominal * discount
                discountPrice.text = getString(R.string.rp_s, formatter.format(afterDiscount))
                subTotalDiscount.text =
                    getString(R.string.discount_s, if (discount == 0.1) "10%" else "5%")
            }
        }
    }

    private fun totalExtraLuggage() {
        val getLuggage = intent?.getParcelableExtra<DataOrder>(EXTRA_DATA_ORDER)?.extraLuggage
        if (getLuggage == true) {
            luggage = 50000.0
            luggagePrice.text = getString(R.string.rp_s, formatter.format(luggage))
        } else {
            luggage = 0.0
            luggagePrice.visibility = View.GONE
            subTotalLuggage.visibility = View.GONE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        paymentMethodSpinner.setErrorEnabled(false)
        when (position) {
            0 -> {
                tvNameOfBank.visibility = View.GONE
                nameOfBank.visibility = View.GONE
                tvAccountNumber.visibility = View.GONE
                accountNumber.visibility = View.GONE
                tvKindOfBank.visibility = View.GONE
                kindOfBank.visibility = View.GONE
                bankAttention.visibility = View.GONE
                cashOutletAttention.visibility = View.GONE
            }
            1 -> {
                tvNameOfBank.visibility = View.VISIBLE
                nameOfBank.visibility = View.VISIBLE
                tvAccountNumber.visibility = View.VISIBLE
                accountNumber.visibility = View.VISIBLE
                tvKindOfBank.visibility = View.VISIBLE
                kindOfBank.visibility = View.VISIBLE
                bankAttention.visibility = View.VISIBLE
                cashOutletAttention.visibility = View.GONE
            }
            2 -> {
                tvNameOfBank.visibility = View.GONE
                nameOfBank.visibility = View.GONE
                tvAccountNumber.visibility = View.GONE
                accountNumber.visibility = View.GONE
                tvKindOfBank.visibility = View.GONE
                kindOfBank.visibility = View.GONE
                bankAttention.visibility = View.GONE
                cashOutletAttention.visibility = View.VISIBLE
            }
        }
    }
}