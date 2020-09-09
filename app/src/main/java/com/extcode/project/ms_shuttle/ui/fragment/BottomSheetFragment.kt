package com.extcode.project.ms_shuttle.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.extcode.project.ms_shuttle.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var mListener: PassDataPassenger? = null

    fun newInstance(): BottomSheetFragment? {
        return BottomSheetFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,
            R.style.DialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PassDataPassenger) {
            mListener = context
        } else {
            throw RuntimeException(
                "$context must implement ItemClickListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ArrayAdapter.createFromResource(
            context as Context,
            R.array.seat,
            android.R.layout.simple_list_item_1
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            seatSpinner.getSpinner().adapter = arrayAdapter
        }

        addDataPassenger.setOnClickListener {
            when {
                passengerName.editText?.text.isNullOrEmpty() -> passengerName.editText?.error =
                    getString(R.string.please_fill_the_passenger_name)
                seatSpinner.getSpinner().selectedItemPosition == 0 -> seatSpinner.setError(getString(
                                    R.string.please_select_the_seat))
                else -> {
                    val getPassengerName = passengerName.editText?.text.toString()
                    val getPassengerSeat = seatSpinner.getSpinner().selectedItem.toString()
                    mListener?.showDataPassenger(getPassengerName, getPassengerSeat, 1)
                    dismiss()
                }
            }
        }
    }

    interface PassDataPassenger {
        fun showDataPassenger(passengerName: String, passengerSeat: String, totalPassenger: Int)
    }
}