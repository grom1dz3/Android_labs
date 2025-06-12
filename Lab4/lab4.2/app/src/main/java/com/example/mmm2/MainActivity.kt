package com.example.mmm2

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.mmm2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ViewPagerAdapter

    // Для оновлення підписів з фрагментів
    var onPageChangedCallback: ((Int) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onPageChangedCallback?.invoke(position)
            }
        })
    }

    fun setPage(page: Int) {
        binding.viewPager.currentItem = page
    }

    fun getCurrentPage(): Int = binding.viewPager.currentItem

    fun goToNextPage() {
        val next = (binding.viewPager.currentItem + 1) % adapter.itemCount
        binding.viewPager.currentItem = next
    }
}
