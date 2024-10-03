package com.example.citassalon.presentacion.features.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.example.domain.entities.remote.migration.Staff


sealed class ClickOnItemStaff {
    data object ClickOnItem : ClickOnItemStaff()
    data object ClickOnImage : ClickOnItemStaff()
}

@Composable
fun ItemStaff(
    modifier: Modifier = Modifier,
    staff: Staff,
    branch: String? = null,
    onClick: ((ClickOnItemStaff) -> Unit?)? = null
) {
    Card(
        onClick = {
            onClick?.invoke(ClickOnItemStaff.ClickOnItem)
        },
        modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imageLoader =
                LocalContext.current.imageLoader.newBuilder().logger(DebugLogger()).build()
            Spacer(modifier = Modifier.height(16.dp))
            SubcomposeAsyncImage(
                imageLoader = imageLoader,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(150.dp)
                    .clickable {
                        onClick?.invoke(ClickOnItemStaff.ClickOnImage)
                    },
                model = staff.image_url,
                contentDescription = "ImageStaff",
                loading = { CircularProgressIndicator() },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = staff.nombre, fontWeight = FontWeight.W900, fontSize = 18.sp)
            branch?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = branch, fontWeight = FontWeight.W900, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun ItemStaffPreview(modifier: Modifier = Modifier) {
    ItemStaff(staff = dummyStaff(), branch = "Zacatecas")
}

fun dummyStaff() =
    Staff(
        id = "",
        image_url = "",
        nombre = "Orlando",
        sexo = "",
        valoracion = 4
    )
