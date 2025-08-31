package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TopBarGenerica(
    goBack: () -> Unit,
    titulo: String,
){
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = { goBack() },
            modifier = Modifier
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = ""
            )
        }
        Text(
            text = titulo,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    TopBarGenerica({}, "Prueba")
}