package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.tarea

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity
import com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.composables.TopBarGenerica

@Composable
fun TareaListScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    goToTarea: (Int?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    DesplegarListado(
        uiState = uiState,
        goToTarea = { goToTarea(it) },
        onDeleteClick = { viewModel.onEvent(TareaEvent.Delete(it)) },
    )
}

@Composable
fun DesplegarListado(
    uiState: TareaUiState,
    goToTarea: (Int?) -> Unit,
    onDeleteClick: (TareaEntity) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    goToTarea(0)
                }
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
        topBar = {
            TopBarGenerica(
                goBack = {},
                titulo = "Listado de Tareas",
                backButtonVisible = false
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .padding(innerPadding)
        ) {
            items(uiState.listaTareas){ tarea ->
                TareaRow(
                    tarea = tarea,
                    onEditClick = { goToTarea(tarea.tareaId) },
                    onDeleteClick = { onDeleteClick(tarea) },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListado() {
    DesplegarListado(
        uiState = TareaUiState(
            listaTareas = listOf(
                TareaEntity(
                    tareaId = 1,
                    descripcion = "Descripcion",
                    tiempo = 1
                ),
                TareaEntity(
                    tareaId = 2,
                    descripcion = "Descripcion",
                    tiempo = 2
                ),
            )
        ),
        onDeleteClick = {},
        goToTarea = {},
    )
}
