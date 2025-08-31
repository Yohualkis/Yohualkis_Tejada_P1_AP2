package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MensajeDeErrorGenerico(
    errorMessage: String?,
) {
    AnimatedVisibility(visible = !errorMessage.isNullOrBlank()) {
        Text(
            text = errorMessage!!,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}