package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.tarea

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
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.yohualkis_tejada_p1_ap2.R
import com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.composables.MensajeDeErrorGenerico
import com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.composables.TopBarGenerica

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
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TareaFormulario(
        uiState = uiState,
        evento = viewModel::onEvent,
        goBack = goBack
    )
    LaunchedEffect(uiState.guardado) {
        if (uiState.guardado) {
            goBack()
            viewModel.onEvent(TareaEvent.GoBackAfterSave)
        }
    }
}

@Composable
fun TareaFormulario(
    uiState: TareaUiState,
    evento: (TareaEvent) -> Unit,
    goBack: () -> Unit,
) {
    val buscadorFocus = remember { FocusRequester() }
    val manejadorFocus = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopBarGenerica(
                titulo = stringResource(R.string.atras),
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
                    manejadorFocus.clearFocus()
                },
        ) {
            Text(
                text = if (uiState.tareaId != null && uiState.tareaId != 0) stringResource(R.string.editar_tarea) else stringResource(R.string.registrar_tarea),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(6.dp))

            // CAMPO DESCRIPCION
            OutlinedTextField(
                value = uiState.descripcion ?: "",
                onValueChange = {
                    evento(TareaEvent.DescripcionChange(it))
                },
                label = { Text(stringResource(R.string.campo_descripcion)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(buscadorFocus),
                singleLine = true,
                isError = !uiState.errorMessageDescripcion.isNullOrBlank(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Article,
                        contentDescription = ""
                    )
                }
            )
            MensajeDeErrorGenerico(uiState.errorMessageDescripcion)

            // CAMPO TIEMPO
            OutlinedTextField(
                value = uiState.tiempo?.toString() ?: "",
                onValueChange = {
                    evento(TareaEvent.TiempoChange(it.toIntOrNull()))
                },
                label = { Text(stringResource(R.string.campo_tiempo)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = !uiState.errorMessageTiempo.isNullOrBlank(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AttachMoney,
                        contentDescription = ""
                    )
                }
            )
            MensajeDeErrorGenerico(uiState.errorMessageTiempo)

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
                            manejadorFocus.clearFocus()
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
                            contentDescription = stringResource(R.string.limpiar),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(
                            text = stringResource(R.string.limpiar),
                            fontSize = 16.sp
                        )
                    }
                }

                Column {
                    Button(
                        onClick = {
                            evento(TareaEvent.Save)
                            manejadorFocus.clearFocus()
                        },
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = stringResource(R.string.guardar),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(
                            text = stringResource(R.string.guardar),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}