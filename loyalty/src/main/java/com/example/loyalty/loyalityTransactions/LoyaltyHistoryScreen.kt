package com.example.loyalty.loyalityTransactions


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.theme.Background
import com.example.domain.loyalty.LoyaltyTransaction
import com.example.domain.loyalty.LoyaltyTransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LoyaltyHistoryScreen(
    navController: NavHostController,
    viewModel: LoyaltyHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true,
            title = "Mi Actividad"
        )
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Background)) {
            if (uiState.transactions.isEmpty() && !uiState.isLoading) {
                EmptyHistory()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.transactions) { transaction ->
                        TransactionItem(transaction)
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionItem(transaction: LoyaltyTransaction) {
    val isEarned = transaction.type == LoyaltyTransactionType.APPOINTMENT_EARNED
    val color = if (isEarned) Color(0xFF4CAF50) else Color(0xFFF44336)
    val icon = if (isEarned) Icons.Default.Add else Icons.Default.Remove

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = formatDate(transaction.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
            }

            Text(
                text = "${if (isEarned) "+" else ""}${transaction.points} ⭐",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = color
            )
        }
    }
}

@Composable
private fun EmptyHistory() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Aún no tienes movimientos", color = Color.Gray)
    }
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(timestamp))
}