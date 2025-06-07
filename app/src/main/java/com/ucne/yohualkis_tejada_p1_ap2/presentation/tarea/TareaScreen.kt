package com.ucne.yohualkis_tejada_p1_ap2.presentation.tarea

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yohualkis_tejada_p1_ap2.presentation.composables.TopBarGenerica
import com.ucne.yohualkis_tejada_p1_ap2.presentation.navigation.UiEvent

@Composable
fun TareaScreen(
    viewModel: TareaViewModel = hiltViewModel(),
    tareaId: Int?,
    goBack: () -> Unit,
) {
    LaunchedEffect(tareaId) {
        tareaId?.let {
            viewModel.selectedTarea(tareaId)
        }
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> goBack()
            }
        }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TareaFormulario(
        uiState = uiState,
        evento = viewModel::onEvent,
        goBack = goBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaFormulario(
    uiState: TareaUiState,
    evento: (TareaEvent) -> Unit,
    goBack: () -> Unit,
) {
    val BuscadorFocus = remember { FocusRequester() }
    val ManejadorFocus = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopBarGenerica(
                titulo = "Atrás",
                goBack = goBack
            )
        },
    ) { padding ->
        Spacer(Modifier.height(128.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .padding(padding)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    ManejadorFocus.clearFocus()
                },
        ) {
            Text(
                text = if (uiState.tareaId != null && uiState.tareaId != 0) "Editar tarea" else "Registrar tarea",
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(6.dp))

            // CAMPO DESCRIPCION
            OutlinedTextField(
                value = uiState.descripcion ?: "",
                onValueChange = {
                    evento(TareaEvent.LimpiarErrorMessageDescripcion)
                    evento(TareaEvent.DescripcionChange(it))
                },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(BuscadorFocus),
                singleLine = true,
                isError = uiState.descripcion.isNullOrBlank() && !uiState.errorMessageDescripcion.isNullOrBlank(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Article,
                        contentDescription = ""
                    )
                }
            )
//            MensajeDeErrorGenerico(uiState.errorMessage)

            // CAMPO TIEMPO
            OutlinedTextField(
                value = uiState.tiempo.toString(),
                onValueChange = {
                    evento(TareaEvent.LimpiarErrorMessageTiempo)
                    evento(TareaEvent.TiempoChange(it.toInt()))
                },
                label = { Text("Tiempo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = (uiState.tiempo ?: 0) <= 0 && !uiState.errorMessageTiempo.isNullOrBlank(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = ""
                    )
                }
            )

//            MensajeDeErrorGenerico(uiState.errorMessage)

            Spacer(modifier = Modifier.height(8.dp))

            // BOTONES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Button(
                        onClick = {
                            evento(TareaEvent.LimpiarTodo)
                            ManejadorFocus.clearFocus()
                        },
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CleaningServices,
                            contentDescription = "Limpiar",
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(
                            text = "Limpiar",
                            fontSize = 16.sp
                        )
                    }
                }

                Column {
                    Button(
                        onClick = {
                            evento(TareaEvent.Save)
                            ManejadorFocus.clearFocus()
                        },
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Guardar",
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(
                            text = "Guardar",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}