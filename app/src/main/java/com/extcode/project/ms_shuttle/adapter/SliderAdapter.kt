package com.extcode.project.ms_shuttle.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.entity.DataImageSlider
import kotlinx.android.synthetic.main.slider_items.view.*

class SliderAdapter(private val sliderItems: ArrayList<DataImageSlider>, val viewPager2: ViewPager2): RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.slider_items,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sliderItems[position])
        if (position == sliderItems.size - 2){
            viewPager2.post(holder.runnable)
        }
    }

    override fun getItemCount(): Int = sliderItems.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(dataImageSlider: DataImageSlider){
            with(itemView){
                Glide.with(itemView.context)
                    .load(dataImageSlider.image)
                    .into(imageSlide)
            }
        }
        val runnable = Runnable {
            sliderItems.addAll(sliderItems)
            notifyDataSetChanged()
        }
    }
}