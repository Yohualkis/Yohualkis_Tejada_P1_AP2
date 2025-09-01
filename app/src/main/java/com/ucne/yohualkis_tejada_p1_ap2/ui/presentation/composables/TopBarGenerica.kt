package com.ucne.yohualkis_tejada_p1_ap2.ui.presentation.composables

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ucne.yohualkis_tejada_p1_ap2.R

@Composable
fun TopBarGenerica(
    goBack: () -> Unit,
    titulo: String,
    backButtonVisible: Boolean = true
) {
    TopAppBar(
        title = {
            Text(
                text = titulo,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = if (backButtonVisible) {
            {
                IconButton(
                    onClick = { goBack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.atras)
                    )
                }
            }
        } else null,
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        elevation = 4.dp,
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    TopBarGenerica({}, "Prueba")
}