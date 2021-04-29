package org.d3if4039.hitungbmi.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4039.hitungbmi.R
import org.d3if4039.hitungbmi.data.KategoriBmi
import org.d3if4039.hitungbmi.databinding.FragmentHitungBinding
import org.d3if4039.hitungbmi.db.BmiDb


class HitungFragment : Fragment() {
    private val viewModel: HitungViewModel by lazy {
        val db = BmiDb.getInstance(requireContext())
        val factory =
            HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory).get(HitungViewModel::class.java)
    }
    private lateinit var binding: FragmentHitungBinding

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
                return true
            }
            R.id.menu_about -> {
                findNavController().navigate(R.id.action_hitungFragment_to_aboutFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNavigasi().observe(viewLifecycleOwner) {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections.actionHitungFragmentToSaranFragment(it)
            )
            viewModel.selesaiNavigasi()
        }

        viewModel.getHasilBmi().observe(viewLifecycleOwner) {
            if (it == null) return@observe

            with(binding) {
                tvBmi.text = getString(R.string.bmi_x, it.bmi)
                tvKategori.text = getString(R.string.kategori_x, getKategori(it.kategori))
                buttonGroup.visibility = View.VISIBLE
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)

        with(binding) {
            button.setOnClickListener { hitungBmi() }
            saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
            shareButton.setOnClickListener { shareData() }
        }
        setHasOptionsMenu(true)
        return binding.root
    }


    private fun hitungBmi() {
        val berat = binding.beratB.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_SHORT).show()
            return
        }

        val tinggi = binding.tinggiEditText.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_SHORT).show()
            return
        }

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_SHORT).show()
            return
        }
        val isMale = selectedId == R.id.priaRadioButton
        viewModel.hitungBmi(berat, tinggi, isMale)
    }

    private fun shareData() {
        val selectedId =
            binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.priaRadioButton)
            getString(R.string.Pria)
        else
            getString(R.string.Wanita)

        val message = getString(
            R.string.bagikan_template,
            binding.beratB.text,
            binding.tinggiEditText.text,
            gender,
            binding.tvBmi.text,
            binding.tvKategori.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager
            ) != null
        ) {
            startActivity(shareIntent)
        }
    }


    private fun getKategori(kategori: KategoriBmi): String {

        val stringRes = when (kategori) {
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }
}