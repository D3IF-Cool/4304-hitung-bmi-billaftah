package org.d3if4039.hitungbmi.ui.histori


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if4039.hitungbmi.db.BmiDao

class HistoriViewModelFactory (private val db: BmiDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoriViewModel::class.java)) {
            return HistoriViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}