package com.example.unitconverterapp.compose.converter

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.unitconverterapp.data.Conversion
import java.math.RoundingMode
import java.text.DecimalFormat

//使用者剛進去app時不顯示input field 與 button
//只有當使用者選擇時才顯示
//所以要check selectedConversion是否為空.

//在螢幕旋轉時，typedValue與selectedConversion會再一次從view model中拿資料，導致資料重複。
//為了解決這個問題可以用一個boolean變數
@Composable
fun TopScreen(
    list: List<Conversion>,
    selectedConversion : MutableState<Conversion?>,
    inputText : MutableState<String>,
    typedValue : MutableState<String>,
    isLandscape : Boolean,
    save : (String,String)->Unit
    ) {

    //使用者輸入時為真
    var toSave by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        //螢幕旋轉後這些local的會不見，所以要使用view model
//    val selectedConversion : MutableState<Conversion?> = remember {
//        mutableStateOf(null) //可能為空值，使用者可能啥都沒選
//    }
//    val inputText = remember {
//        mutableStateOf("")
//    }
//    val typedValue = remember {
//        mutableStateOf("0.0")
//    }

//    ConversionMenu(list = list, convert = {}) //lambda在最後可以使用下面寫法
        ConversionMenu(list = list,isLandscape) {
            //此處的it就是選中的conversion
            selectedConversion.value = it
            //當使用者從選項單選中conversion後，要將typedValue設為初始值，不然當recomposition時會重複輸入資料
            typedValue.value = "0.0"
        }

        selectedConversion.value?.let {
            InputBlock(conversion = it, inputText = inputText,modifier = Modifier, isLandscape = isLandscape){ input->
                Log.i("LinLi", "User typed $input");
                //將從lambda得到的input assign
                typedValue.value = input
                toSave = true
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

            if (toSave){
                save(message1,message2)
                ResultBlock(message1 = message1, message2 = message2)
                toSave = false
            }
        }
    }


}