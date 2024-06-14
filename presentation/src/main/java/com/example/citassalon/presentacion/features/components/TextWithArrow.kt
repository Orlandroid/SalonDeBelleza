package com.example.citassalon.presentacion.features.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.citassalon.R

@Composable
fun TextWithArrow(
    config: TextWithArrowConfig
) {
    Card(elevation = CardDefaults.cardElevation(config.cardElevation),
        shape = RoundedCornerShape(config.cornerSize),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = { config.clickOnItem.invoke() }) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = config.verticalPadding)
        ) {
            val (row, icon) = createRefs()
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.constrainAs(row) {
                    start.linkTo(parent.start)
                    end.linkTo(icon.start, 16.dp)
                    width = Dimension.matchParent
                }) {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = config.text, fontSize = config.fontSize
                )
            }
            Icon(painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.constrainAs(icon) {
                    end.linkTo(parent.end, 16.dp)
                    linkTo(parent.top, parent.bottom)
                })
        }
    }
}

data class TextWithArrowConfig(
    val text: String,
    val fontSize: TextUnit = 20.sp,
    val cardElevation: Dp = 6.dp,
    val cornerSize: Dp = 8.dp,
    val verticalPadding: Dp = 16.dp,
    val clickOnItem: () -> Unit
)

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TextWithArrowPreview() {
    TextWithArrow(TextWithArrowConfig(stringResource(id = R.string.sucursales)) {})
}