package com.extcode.project.ms_shuttle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.entity.ListOrder
import kotlinx.android.synthetic.main.list_order_items.view.*
import java.util.*

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var history = ArrayList<ListOrder>()
        set(history) {
            this.history.clear()
            this.history.addAll(history)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_order_items,parent, false))
    }

    override fun getItemCount(): Int = history.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(history[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(listOrder: ListOrder){
            with(itemView){
                orderCode.text = String.format(Locale.getDefault(), "#%04d", listOrder.bookingCode)
                pickUp.text = listOrder.pickUp
                date.text = listOrder.date
                time.text = listOrder.time
                destination.text = listOrder.destination

                itemView.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.up_anim)

            }
        }
    }
}