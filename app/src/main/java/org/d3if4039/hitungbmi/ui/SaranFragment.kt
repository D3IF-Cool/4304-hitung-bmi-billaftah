package org.d3if4039.hitungbmi.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if4039.hitungbmi.R
import org.d3if4039.hitungbmi.data.KategoriBmi
import org.d3if4039.hitungbmi.databinding.FragmentSaranBinding

class SaranFragment : Fragment() {
    private val args: SaranFragmentArgs by navArgs()
    private lateinit var binding: FragmentSaranBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaranBinding.inflate(
            layoutInflater, container, false
        )
        var hasil: KategoriBmi = KategoriBmi.KURUS
        when (arguments?.getString("iniBmi")) {
            "KURUS" -> hasil = KategoriBmi.KURUS
            "IDEAL" -> hasil = KategoriBmi.IDEAL
            "GEMUK" -> hasil = KategoriBmi.GEMUK
        }
        binding.tvHasilBerat.text = arguments?.getString("iniBerat")
        binding.tvHasilTinggi.text = arguments?.getString("iniTinggi")
        binding.tvJk.text = arguments?.getString("jenisKelamin")
        binding.tvBmii.text = hasil.toString()

        updateUI(hasil)
        binding.sharing.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Halo, sini yuk :)"
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Haloo hey"))
        }
        return binding.root
    }

    private fun updateUI(kategori: KategoriBmi) {
        val actionBar = (requireActivity() as AppCompatActivity)
            .supportActionBar
        when (kategori) {
            KategoriBmi.KURUS -> {
                actionBar?.title =
                    getString(R.string.judul_kurus)
                binding.imageView.setImageResource(R.drawable.kurus)
                binding.textView.text = getString(R.string.saran_kurus)
            }
            KategoriBmi.IDEAL -> {
                actionBar?.title =
                    getString(R.string.judul_ideal)
                binding.imageView.setImageResource(R.drawable.ideal)
                binding.textView.text = getString(R.string.saran_ideal)
            }
            KategoriBmi.GEMUK -> {
                actionBar?.title =
                    getString(R.string.judul_gemuk)
                binding.imageView.setImageResource(R.drawable.gemuk)
                binding.textView.text = getString(R.string.saran_gemuk)
            }
        }
    }
}