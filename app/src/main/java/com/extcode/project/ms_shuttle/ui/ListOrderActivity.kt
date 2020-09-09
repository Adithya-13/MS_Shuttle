package com.extcode.project.ms_shuttle.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_list_order.*

class ListOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_order)

        configViewPager()
    }

    private fun configViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        })
        overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
        finish()
    }

}