package org.d3if4039.hitungbmi.ui.histori

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var myAdapter: HistoriAdapter

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
        myAdapter = HistoriAdapter()
        with(binding.recyclerView) {
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    RecyclerView.VERTICAL
                )
            )
            adapter = myAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility =
                if (it.isEmpty()) View.VISIBLE else View.GONE
            myAdapter.updateData(it)
        })
    }
}