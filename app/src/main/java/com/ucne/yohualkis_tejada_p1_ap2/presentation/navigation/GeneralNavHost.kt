package com.ucne.yohualkis_tejada_p1_ap2.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ucne.yohualkis_tejada_p1_ap2.presentation.tarea.TareaListScreen
import com.ucne.yohualkis_tejada_p1_ap2.presentation.tarea.TareaScreen


@Composable
fun GeneralNavHost(
    navHostController: NavHostController
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.TareasList
    ){

        // Lista de entidades
        composable<Screen.TareasList> {
            TareaListScreen(
                goToTarea = { navHostController.navigate(Screen.Tarea(it)) },
            )
        }

        // Entidad formulario
        composable<Screen.Tarea> { backStack ->
            val args = backStack.toRoute<Screen.Tarea>()
            TareaScreen(
                tareaId = args.tareaId,
                goBack = { navHostController.navigateUp() }
            )
        }
    }
}