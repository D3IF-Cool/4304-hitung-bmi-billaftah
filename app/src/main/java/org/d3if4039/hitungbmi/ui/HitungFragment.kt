package org.d3if4039.hitungbmi.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.d3if4039.hitungbmi.R
import org.d3if4039.hitungbmi.data.KategoriBmi
import org.d3if4039.hitungbmi.databinding.FragmentHitungBinding

class HitungFragment : Fragment() {
    private val viewModel: HitungViewModel by viewModels()
    private lateinit var binding: FragmentHitungBinding
    private lateinit var kategoriBmi: KategoriBmi

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_hitungFragment_to_aboutFragment
            )
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener { hitungBmi() }
        binding.saranButton.setOnClickListener { view: View ->
            val bundle = Bundle()
            bundle.putString("iniBerat", binding.beratB.text.toString())
            bundle.putString("iniTinggi", binding.tinggiB.text.toString())

            var gender = ""
            var katBmi = ""
            when {
                binding.priaRadioButton.isChecked -> gender = "Pria"
                binding.wanitaRadioButton.isChecked -> gender = "Wanita"
            }
            bundle.putString("jenisKelamin", gender)
            when (kategoriBmi) {
                KategoriBmi.KURUS -> katBmi = "KURUS"
                KategoriBmi.IDEAL -> katBmi = "IDEAL"
                KategoriBmi.GEMUK -> katBmi = "GEMUK"
            }
            bundle.putString("iniBmi", katBmi)

            view.findNavController().navigate(
                R.id.action_hitungFragment_to_saranFragment, bundle
            )
        }
        binding.shareButton.setOnClickListener { shareData() }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel . getHasilBmi ().observe(
            viewLifecycleOwner,
            {
                if (it == null) return@observe
                    binding.tvBmi.text = getString(R.string.bmi_x, it.bmi)
                binding . tvKategori . text = getString (R.string.kategori_x,
                    getKategori(it.kategori))
                binding.buttonGroup.visibility = View.VISIBLE
            })
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