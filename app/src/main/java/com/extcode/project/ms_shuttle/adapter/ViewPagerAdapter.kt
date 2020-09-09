package com.extcode.project.ms_shuttle.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.ui.fragment.HistoryFragment
import com.extcode.project.ms_shuttle.ui.fragment.OnGoingFragment

class ViewPagerAdapter(private val context: Context, fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.onGoingTitle,
        R.string.historyTitle
    )

    private val fragment = listOf(
        OnGoingFragment(),
        HistoryFragment()
    )

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(tabTitles[position])
    }

    override fun getCount(): Int = 2
}