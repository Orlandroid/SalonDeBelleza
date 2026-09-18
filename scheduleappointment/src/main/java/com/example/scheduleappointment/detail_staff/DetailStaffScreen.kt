package com.example.scheduleappointment.detail_staff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.scheduleappointment.R
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.theme.Background
import com.example.domain.entities.Review
import com.example.domain.entities.StaffRatingSummary
import com.example.domain.entities.remote.migration.Staff
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DetailStaffScreen(
    navController: NavController,
    viewModel: DetailStaffViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.detail_staff))
    ) {
        uiState.currentStaff?.let { currentStaff ->
            DetailStaffScreenContent(
                staff = currentStaff,
                ratingSummary = uiState.ratingSummary
            )
        }
    }
}

@Composable
private fun DetailStaffScreenContent(
    modifier: Modifier = Modifier,
    staff: Staff,
    ratingSummary: StaffRatingSummary
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            AsyncImage(
                model = staff.image_url,
                contentDescription = "Staff image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )

            Text(
                modifier = Modifier.padding(top = 24.dp),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                text = staff.name.orEmpty()
            )

            StaffRatingHeader(ratingSummary)

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (ratingSummary.recentComments.isNotEmpty()) {
            item {
                Text(
                    text = stringResource(R.string.recent_feedback),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            items(ratingSummary.recentComments) { review ->
                ReviewItem(review)
            }
        } else {
            item {
                Text(
                    text = stringResource(R.string.no_reviews_yet),
                    modifier = Modifier.padding(top = 32.dp),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun StaffRatingHeader(summary: StaffRatingSummary) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingBar(
                value = summary.averageRating.toFloat(),
                style = RatingBarStyle.Fill(),
                onValueChange = {},
                onRatingChanged = {},
                size = 24.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "%.1f".format(summary.averageRating),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Text(
            text = "(${summary.totalReviews} reviews)",
            color = Color.Gray,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.userName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = formatDate(review.createdAt),
                    color = Color.Gray,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            RatingBar(
                value = review.rating.toFloat(),
                style = RatingBarStyle.Fill(),
                onValueChange = {},
                onRatingChanged = {},
                size = 14.dp
            )

            if (review.comment.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = review.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

@Composable
@Preview(showBackground = true)
private fun DetailStaffScreenPreview() {
    DetailStaffScreenContent(
        staff = Staff.mockStaff(),
        ratingSummary = StaffRatingSummary(4.5, 12, listOf())
    )
}
