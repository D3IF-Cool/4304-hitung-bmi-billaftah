package org.d3if4039.hitungbmi.ui.histori

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if4039.hitungbmi.databinding.FragmentHistoryBinding
import org.d3if4039.hitungbmi.db.BmiDb

class HistoriFragment : Fragment() {
    private val viewModel: HistoriViewModel by lazy {
        val db = BmiDb.getInstance(requireContext())
        val factory =
            HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HistoriViewModel::class.java)
    }

    private lateinit var binding: FragmentHistoryBinding

    override
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(
            layoutInflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {
            Log.d(
                "HistoriFragment",
                "Jumlah data: ${it.size}"
            )
        })
    }
}