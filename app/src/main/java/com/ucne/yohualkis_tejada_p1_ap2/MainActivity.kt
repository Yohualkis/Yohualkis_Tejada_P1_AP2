package com.ucne.yohualkis_tejada_p1_ap2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.navigation.GeneralNavHost
import com.ucne.yohualkis_tejada_p1_ap2.ui.theme.YohualkisTejadaP1AP2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YohualkisTejadaP1AP2Theme {
                val nav = rememberNavController()
                Scaffold { paddingValues ->
                    Box(Modifier.fillMaxSize().padding(paddingValues)) {
                        GeneralNavHost(nav)
                    }
                }
            }
        }
    }
}