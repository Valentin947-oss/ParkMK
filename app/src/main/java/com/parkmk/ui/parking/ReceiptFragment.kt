package com.parkmk.ui.parking

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.parkmk.R
import com.parkmk.databinding.FragmentReceiptBinding

class ReceiptFragment : Fragment(R.layout.fragment_receipt) {
    private var _b: FragmentReceiptBinding? = null
    private val b get() = _b!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentReceiptBinding.bind(view)
        b.btnBackToMap.setOnClickListener {
            findNavController().navigate(R.id.historyFragment)
        }
    }
    override fun onDestroyView() { super.onDestroyView(); _b = null }
}
