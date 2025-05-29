package com.ucne.yohualkis_tejada_p1_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ucne.yohualkis_tejada_p1_ap2.presentation.entidadFutura.EntidadListScreen
import com.ucne.yohualkis_tejada_p1_ap2.presentation.entidadFutura.EntidadScreen


@Composable
fun GeneralNavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.SistemaList
    ){

        // Lista de entidades
        composable<Screen.SistemaList> {
            EntidadListScreen(
                goToEntidad = { navHostController.navigate(Screen.Sistema(it)) },
                goBack = { navHostController.navigateUp() }
            )
        }

        // Entidad formulario
        composable<Screen.Sistema> { backStack ->
            val args = backStack.toRoute<Screen.Sistema>()
            EntidadScreen(
                entidadId = args.sistemaId,
                goBack = { navHostController.navigateUp() }
            )
        }
    }
}