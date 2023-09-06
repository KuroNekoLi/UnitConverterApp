package com.example.unitconverterapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.math.RoundingMode
import java.text.DecimalFormat

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
    val typedValue = remember {
        mutableStateOf("0.0")
    }

//    ConversionMenu(list = list, convert = {}) //lambda在最後可以使用下面寫法
    ConversionMenu(list = list) {
        //此處的it就是選中的conversion
        selectedConversion.value = it
    }

    selectedConversion.value?.let {
        InputBlock(conversion = it, inputText = inputText,modifier = Modifier){ input->
            Log.i("LinLi", "User typed $input");
            //將從lambda得到的input assign
            typedValue.value = input
        }
    }

    if (typedValue.value != "0.0"){
        //將使用者的輸入轉成double
        val input = typedValue.value.toDouble()
        val multiply = selectedConversion.value!!.multiplyBy
        val result = input*multiply //可能是一個很多位小數

        //以下是將小數擷取四位的方法
        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.DOWN //無條件捨去
        val roundedResult = df.format(result)

        val message1 = "${typedValue.value} ${selectedConversion.value!!.convertFrom} is equal to"
        val message2 = "$roundedResult ${selectedConversion.value!!.convertTo}"
        ResultBlock(message1 = message1, message2 = message2)
    }
}