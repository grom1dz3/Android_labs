package com.example.mmm2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val fragments = listOf(
        FragmentImage.newInstance(R.drawable.image1, "Малюнок 1"),
        FragmentImage.newInstance(R.drawable.image2, "Малюнок 2"),
        FragmentImage.newInstance(R.drawable.image3, "Малюнок 3"),
    )

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
