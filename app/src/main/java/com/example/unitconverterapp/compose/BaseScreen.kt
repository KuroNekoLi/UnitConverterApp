package com.example.unitconverterapp.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverterapp.ConverterViewModel

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier, //將預設的modifier設為第一個參數
    converterViewModel: ConverterViewModel = viewModel()
){
    val list = converterViewModel.getConversions()
    Column(modifier = Modifier.padding(30.dp)) {
        TopScreen(list)
        Spacer(modifier = Modifier.height(20.dp))
        HistoryScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun BaseScreenPreview() {
    BaseScreen(Modifier, viewModel())
}