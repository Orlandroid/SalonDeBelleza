package com.example.profile.historial_citas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.profile.R

@Composable
fun RatingDialog(
    staffName: String,
    onDismiss: () -> Unit,
    onSubmit: (Int, String) -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.rate_your_experience)) },
        text = {

            RatingDialogContent(
                staffName = staffName,
                rating = rating,
                comment = comment,
                onValueChange = {
                    comment = it
                },
                onRatingChanged = {
                    rating = it
                }
            )
        },
        confirmButton = {
            Button(onClick = {
                onSubmit(
                    rating, comment
                )
            }) { Text(text = stringResource(R.string.submit)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(text = stringResource(R.string.maybe_later)) }
        })
}

@Composable
private fun RatingDialogContent(
    staffName: String,
    rating: Int,
    comment: String,
    onValueChange: (String) -> Unit,
    onRatingChanged: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.service_rating_question, staffName))

        StartsIcon(rating = rating, onRatingChanged = onRatingChanged)

        InputComment(comment = comment, onValueChange = onValueChange)

        Text(
            text = stringResource(R.string.earn_stars_for_feedback),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun StartsIcon(
    rating: Int, onRatingChanged: (Int) -> Unit
) {
    Row(modifier = Modifier.padding(vertical = 16.dp)) {
        (1..5).forEach { index ->
            Icon(
                imageVector = if (index <= rating) Icons.Filled.Star else Icons.Default.StarBorder,
                contentDescription = null,
                tint = if (index <= rating) Color(0xFFFFD700) else Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChanged(index) })
        }
    }
}


@Composable
private fun InputComment(
    comment: String, onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = comment,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(R.string.write_a_comment_optional)) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
@Preview(showSystemUi = true)
private fun RatingDialogContentPreview() {
    RatingDialog(onSubmit = { rating, comment ->
    }, onDismiss = {}, staffName = "John Doe")
}