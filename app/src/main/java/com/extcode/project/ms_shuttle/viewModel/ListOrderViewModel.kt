package com.extcode.project.ms_shuttle.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.extcode.project.ms_shuttle.db.ListOrderDatabase
import com.extcode.project.ms_shuttle.entity.ListOrder

class ListOrderViewModel : ViewModel() {


    fun getQueryAll(context: Context): LiveData<List<ListOrder>> {

        val db = ListOrderDatabase.getInstance(context)
        return db.listOrderDao().query()
    }

    fun getQueryById(context: Context, id: String): LiveData<ListOrder> {

        val db = ListOrderDatabase.getInstance(context)
        return db.listOrderDao().queryById(id)
    }

}