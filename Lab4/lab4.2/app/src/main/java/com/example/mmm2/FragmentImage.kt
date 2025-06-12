package com.example.mmm2

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mmm2.databinding.FragmentImageBinding

class FragmentImage : Fragment() {

    private var imageResId = 0
    private var labelText = ""

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_IMAGE_RES = "image_res"
        private const val ARG_LABEL = "label"

        fun newInstance(imageResId: Int, label: String) = FragmentImage().apply {
            arguments = Bundle().apply {
                putInt(ARG_IMAGE_RES, imageResId)
                putString(ARG_LABEL, label)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageResId = it.getInt(ARG_IMAGE_RES)
            labelText = it.getString(ARG_LABEL) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.imageView.setImageResource(imageResId)

        // Ініціалізуємо підписи
        val labels = listOf("Малюнок 1", "Малюнок 2", "Малюнок 3")

        // Очищаємо контейнер і додаємо підписи
        binding.textPanel.removeAllViews()

        labels.forEachIndexed { index, text ->
            val tv = TextView(requireContext()).apply {
                this.text = text
                setPadding(20, 10, 20, 10)
                textSize = 16f
                setTextColor(Color.LTGRAY)
                isClickable = true
                isFocusable = true
                setOnClickListener {
                    (activity as? MainActivity)?.setPage(index)
                }
            }
            binding.textPanel.addView(tv)
        }

        // Виділяємо активний підпис
        highlightLabel((activity as? MainActivity)?.getCurrentPage() ?: 0)

        // Слухаємо зміну сторінки для оновлення підписів
        (activity as? MainActivity)?.onPageChangedCallback = { page ->
            highlightLabel(page)
        }

        // Кнопка "Наступне"
        binding.btnNext.setOnClickListener {
            (activity as? MainActivity)?.goToNextPage()
        }
    }

    private fun highlightLabel(activeIndex: Int) {
        val count = binding.textPanel.childCount
        for (i in 0 until count) {
            val tv = binding.textPanel.getChildAt(i) as TextView
            tv.setTextColor(if (i == activeIndex) Color.WHITE else Color.LTGRAY)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
