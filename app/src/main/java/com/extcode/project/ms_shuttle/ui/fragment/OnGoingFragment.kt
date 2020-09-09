package com.extcode.project.ms_shuttle.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.adapter.OnGoingAdapter
import com.extcode.project.ms_shuttle.entity.ListOrder
import com.extcode.project.ms_shuttle.ui.DetailOrderActivity
import com.extcode.project.ms_shuttle.ui.DetailOrderActivity.Companion.EXTRA_DETAIL_ORDER
import com.extcode.project.ms_shuttle.viewModel.ListOrderViewModel
import kotlinx.android.synthetic.main.fragment_on_going.*

class OnGoingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_going, container, false)
    }

    private lateinit var listOrderViewModel: ListOrderViewModel
    private lateinit var adapter: OnGoingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configRecyclerView()
        configQueryAll()
    }

    override fun onResume() {
        super.onResume()
        configRecyclerView()
        configQueryAll()
    }

    private fun configQueryAll() {

        progressBar.visibility = View.VISIBLE

        listOrderViewModel = ViewModelProvider(this).get(ListOrderViewModel::class.java)
        listOrderViewModel.getQueryAll(context as Context).observe(viewLifecycleOwner, {

            if (it.isNotEmpty()) {
                adapter.onGoing = it as ArrayList<ListOrder>
                progressBar.visibility = View.GONE
                lottieNotFound.visibility = View.GONE
                tvConfirm.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                lottieNotFound.visibility = View.VISIBLE
                tvConfirm.visibility = View.GONE
                adapter.onGoing = it as ArrayList<ListOrder>
            }

        })

    }

    private fun configRecyclerView() {

        adapter = OnGoingAdapter()

        onGoingRecyclerview.layoutManager = LinearLayoutManager(context)
        onGoingRecyclerview.adapter = adapter

        adapter.setOnItemClickCallback(object : OnGoingAdapter.OnItemClickCallback {
            override fun selectedData(listOrder: ListOrder) {
                showSelectedData(listOrder)
            }
        })
    }

    private fun showSelectedData(listOrder: ListOrder) {

        val detailIntent = Intent(context, DetailOrderActivity::class.java)
        detailIntent.putExtra(EXTRA_DETAIL_ORDER, listOrder)
        startActivity(detailIntent)

    }

}