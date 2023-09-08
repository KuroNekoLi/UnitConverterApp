package com.example.unitconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.unitconverterapp.compose.BaseScreen
import com.example.unitconverterapp.data.ConverterDatabase
import com.example.unitconverterapp.data.ConverterRepositoryImpl
import com.example.unitconverterapp.ui.theme.UnitConverterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //需要創建一個ConverterViewModel的實例給MainActivity當參數
        //要創建ConverterViewModel的實例需要ConverterRepository的實例
        //要創建ConverterRepository的實例(ConverterRepositoryImpl)，需要ConverterDao的實例
        //要得到ConverterDao的實例可以使用ConverterDatabase

        val dao = ConverterDatabase.getInstance(application).converterDao
        val repository = ConverterRepositoryImpl(dao)
        val factory = ConverterViewModelFactory(repository)
        setContent {
            UnitConverterAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                }
                BaseScreen(factory = factory)
            }
        }
    }
}