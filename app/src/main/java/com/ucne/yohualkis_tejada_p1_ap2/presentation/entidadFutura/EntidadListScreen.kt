package com.ucne.yohualkis_tejada_p1_ap2.presentation.entidadFutura

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
fun EntidadListScreen(
    viewModel: ViewModel = hiltViewModel(),
    goToEntidad: (Int?) -> Unit,
    goBack: () -> Unit
){

}