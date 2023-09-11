package com.example.unitconverterapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.unitconverterapp.data.ConverterRepository
import javax.inject.Inject

class ConverterViewModelFactory @Inject constructor(private val converterRepository: ConverterRepository) : NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ConverterViewModel(converterRepository) as T
    }
}