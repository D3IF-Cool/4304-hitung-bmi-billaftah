package org.d3if4039.hitungbmi.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if4039.hitungbmi.data.HasilBmi
import org.d3if4039.hitungbmi.data.HitungBmi
import org.d3if4039.hitungbmi.data.KategoriBmi
import org.d3if4039.hitungbmi.db.BmiDao
import org.d3if4039.hitungbmi.db.BmiEntity

class HitungViewModel(private val db: BmiDao) : ViewModel() {

    private val hasilBmi = MutableLiveData<HasilBmi?>()
    private val navigasi = MutableLiveData<KategoriBmi?>()

    fun hitungBmi(berat: String, tinggi: String, isMale: Boolean) {
        val dataBmi = BmiEntity(
            berat = berat.toFloat(),
            tinggi = tinggi.toFloat(),
            isMale = isMale
        )
        hasilBmi . value = HitungBmi.hitung(dataBmi)

        val launch = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataBmi)
            }
        }
    }

    fun mulaiNavigasi() {
        navigasi.value = hasilBmi.value?.kategori
    }

    fun selesaiNavigasi() {
        navigasi.value = null
    }

    fun getHasilBmi(): LiveData<HasilBmi?> = hasilBmi
    fun getNavigasi(): LiveData<KategoriBmi?> = navigasi
}