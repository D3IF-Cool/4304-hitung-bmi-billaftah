package org.d3if4039.hitungbmi.ui.histori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3if4039.hitungbmi.databinding.FragmentHistoryBinding

class HistoriFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater,
            container, false)
        return binding.root
    }
}