package com.ucne.yohualkis_tejada_p1_ap2.presentation.tarea

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucne.yohualkis_tejada_p1_ap2.data.local.entities.TareaEntity

@Composable
fun TareaRow(
    tarea: TareaEntity,
    onEditClick: (Int?) -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Info
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = tarea.descripcion,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "${tarea.tiempo} minutos",
                        fontSize = 14 .sp,
                        color = Color.DarkGray
                    )
                }

                // BOTONES
                Row {
                    IconButton(onClick = { onEditClick(tarea.tareaId) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    }
                    IconButton(onClick = { onDeleteClick() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareaCardInfoPreview() {
    TareaRow(
        tarea = TareaEntity(
            tareaId = 1,
            descripcion = "Descripcion",
            tiempo = 50
        ),
        onEditClick = {},
        onDeleteClick = {},
    )
}