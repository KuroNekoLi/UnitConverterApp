package com.example.unitconverterapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

//使用者剛進去app時不顯示input field 與 button
//只有當使用者選擇時才顯示
//所以要check selectedConversion是否為空
@Composable
fun TopScreen(list: List<Conversion>) {
    val selectedConversion : MutableState<Conversion?> = remember {
        mutableStateOf(null) //可能為空值，使用者可能啥都沒選
    }
    val inputText = remember {
        mutableStateOf("")
    }

//    ConversionMenu(list = list, convert = {}) //lambda在最後可以使用下面寫法
    ConversionMenu(list = list) {
        //此處的it就是選中的conversion
        selectedConversion.value = it
    }

    selectedConversion.value?.let {
        InputBlock(conversion = it, inputText = inputText, modifier = Modifier)
    }
}