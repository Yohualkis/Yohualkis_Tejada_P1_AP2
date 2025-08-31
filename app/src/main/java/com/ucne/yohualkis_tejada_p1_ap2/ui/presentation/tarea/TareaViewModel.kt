package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.tarea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity
import com.ucne.yohualkis_tejada_p1_ap2.data.repository.TareaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaViewModel @Inject constructor(
    private val repository: TareaRepository,
) : ViewModel() {
    private val uiStatePrivado = MutableStateFlow(TareaUiState())
    val uiState = uiStatePrivado.asStateFlow()

    init {
        getTareas()
    }

    fun onEvent(event: TareaEvent) {
        when (event) {
            is TareaEvent.TareaChange -> onTareaChange(event.tareaId)
            is TareaEvent.DescripcionChange -> onDecripcionChange(event.descripcion)
            is TareaEvent.TiempoChange -> onTiempoChange(event.tiempo)
            TareaEvent.GoBackAfterSave -> onGoBackAfterSave()

            is TareaEvent.Delete -> delete(event.tarea)
            TareaEvent.Save -> save()
            TareaEvent.LimpiarTodo -> limpiarTodosLosCampos()
        }
    }

    private fun onGoBackAfterSave() {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(guardado = false)
            }
        }
    }

    private fun limpiarTodosLosCampos() {
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
            var hayErrores = false

            val descripcion = uiStatePrivado.value.descripcion.orEmpty()
            val tiempo = uiStatePrivado.value.tiempo

            var errorDescripcion: String? = null
            var errorTiempo: String? = null

            when {
                descripcion.isBlank() ->
                    errorDescripcion = "Este campo es obligatorio *"

                descripcion.length > 50 ->
                    errorDescripcion = "La descripción no puede tener más de 50 caracteres *"
            }

            when {
                tiempo == null ->
                    errorTiempo = "Este campo es obligatorio *"

                tiempo <= 0 ->
                    errorTiempo = "El tiempo debe ser mayor a 0 *"
            }

            uiStatePrivado.update {
                it.copy(
                    errorMessageDescripcion = errorDescripcion,
                    errorMessageTiempo = errorTiempo
                )
            }

            hayErrores = errorDescripcion != null || errorTiempo != null

            if (hayErrores) return@launch

            uiStatePrivado.update {
                it.copy(guardado = true)
            }

            repository.save(uiStatePrivado.value.toEntity())
            limpiarTodosLosCampos()
        }
    }

    fun selectedTarea(tareaId: Int) {
        viewModelScope.launch {
            if (tareaId > 0) {
                val tarea = repository.find(tareaId)
                uiStatePrivado.update {
                    it.copy(
                        tareaId = tarea.tareaId,
                        descripcion = tarea.descripcion,
                        tiempo = tarea.tiempo,
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
                it.copy(
                    tiempo = tiempo,
                    errorMessageTiempo = ""
                )
            }
        }
    }

    private fun onDecripcionChange(descripcion: String) {
        viewModelScope.launch {
            uiStatePrivado.update {
                it.copy(
                    descripcion = descripcion,
                    errorMessageDescripcion = ""
                )
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