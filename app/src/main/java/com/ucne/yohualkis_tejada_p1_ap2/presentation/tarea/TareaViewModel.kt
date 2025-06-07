package com.ucne.yohualkis_tejada_p1_ap2.presentation.tarea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity
import com.ucne.yohualkis_tejada_p1_ap2.data.repository.TareaRepository
import com.ucne.yohualkis_tejada_p1_ap2.presentation.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val repository: TareaRepository,
) : ViewModel() {
    private val uiStatePrivado = MutableStateFlow(TareaUiState())
    val uiState = uiStatePrivado.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getTareas()
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.TareaChange -> onTareaChange(event.tareaId)
            is TareaEvent.DescripcionChange -> onDecripcionChange(event.descripcion)
            is TareaEvent.TiempoChange -> onTiempoChange(event.tiempo)

            is TareaEvent.Delete -> delete(event.tarea)
            TareaEvent.Save -> save()
            TareaEvent.LimpiarTodo -> limpiarTodosLosCampos()
            TareaEvent.LimpiarErrorMessageTiempo -> limpiarErrorMessageTiempo()
            TareaEvent.LimpiarErrorMessageDescripcion -> limpiarErrorMessageDescripcion()
        }
    }

    private fun limpiarTodosLosCampos(){
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(
                    errorMessageTiempo = "",
                    errorMessageDescripcion = "",
                    tiempo = null,
                    descripcion = ""
                )
            }
        }
    }

    private fun limpiarErrorMessageTiempo() {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(errorMessageTiempo = "")
            }
        }
    }

    private fun limpiarErrorMessageDescripcion() {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(errorMessageDescripcion = "")
            }
        }
    }

    private fun getTareas() {
        viewModelScope.launch {
            repository.getAll().collect { lista ->
                uiStatePrivado.update {
                    it.copy(listaTareas = lista)
                }
            }
        }
    }

    private fun TareaUiState.toEntity() =
        TareaEntity(
            tareaId = tareaId,
            descripcion = descripcion ?: "",
            tiempo = tiempo ?: 0
        )

    private fun save() {
        viewModelScope.launch {
            limpiarTodosLosCampos()

            if (uiStatePrivado.value.descripcion.isNullOrBlank()) {
                uiStatePrivado.update {
                    it.copy(errorMessageDescripcion = "Este campo es obligatorio *")
                }
                return@launch
            }

            if (uiStatePrivado.value.descripcion?.length!! > 50) {
                uiStatePrivado.update {
                    it.copy(errorMessageDescripcion = "La descripción no puede tener más de 50 caracteres *")
                }
                return@launch
            }

            if (uiStatePrivado.value.tiempo.toString().isBlank()) {
                uiStatePrivado.update {
                    it.copy(errorMessageTiempo = "Este campo es obligatorio *")
                }
                return@launch
            }

            if (uiStatePrivado.value.tiempo!! <= 0) {
                uiStatePrivado.update {
                    it.copy(errorMessageTiempo = "El tiempo debe ser mayor a 0 *")
                }
                return@launch
            }
            repository.save(uiStatePrivado.value.toEntity())
            limpiarTodosLosCampos()
            _uiEvent.send(UiEvent.NavigateUp)
        }
    }

    fun selectedTarea(tareaId: Int) {
        viewModelScope.launch {
            if (tareaId > 0) {
                val tarea = repository.find(tareaId)
                uiStatePrivado.update {
                    it.copy(
                        tareaId = tarea?.tareaId,
                        descripcion = tarea?.descripcion,
                        tiempo = tarea?.tiempo,
                    )
                }
            }
        }
    }

    private fun delete(tarea: TareaEntity) {
        viewModelScope.launch {
            repository.delete(tarea)
            getTareas()
        }
    }

    private fun onTiempoChange(tiempo: Int) {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(tiempo = tiempo)
            }
        }
    }

    private fun onDecripcionChange(descripcion: String) {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(descripcion = descripcion)
            }
        }
    }


    private fun onTareaChange(id: Int?) {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(tareaId = id)
            }
        }
    }
}