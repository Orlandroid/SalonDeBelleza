package com.example.wallet.transactions_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.base.BaseScreenState
import com.example.core.ui.base.getContentOrNull
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.theme.AlwaysWhite
import com.example.core.ui.theme.Background
import com.example.core.util.toCurrencyString
import com.example.domain.transaction.Transaction
import com.example.domain.transaction.TransactionType
import com.example.domain.wallet.Currency
import com.example.wallet.R
import com.example.wallet.components.TransactionDetailScreenSkeleton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionDetailScreen(
    navController: NavController,
    transactionId: String,
    viewModel: TransactionDetailViewmodel = hiltViewModel(
        creationCallback = { factory: TransactionDetailViewModelFactory ->
            factory.create(transactionId)
        })
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    when (viewState) {
        BaseScreenState.OnLoading -> {
            TransactionDetailScreenSkeleton()
        }

        is BaseScreenState.OnContent -> {

            viewState.getContentOrNull()?.let { state ->
                BaseComposeScreen(
                    toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.transactions)),
                    navController = navController
                ) {
                    TransactionDetailScreenContent(transaction = state.transaction)
                }
            }

        }


        is BaseScreenState.OnError -> {
            BaseErrorScreen()
        }
    }

}

@Composable
private fun TransactionDetailScreenContent(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        TransactionHeader(transaction = transaction)

        Spacer(modifier = Modifier.height(24.dp))

        TransactionDetailsCard(transaction = transaction)
    }
}

@Composable
private fun TransactionHeader(transaction: Transaction) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(color = Color(0xFFEFEFEF), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val icon = when (transaction.transactionType) {
                TransactionType.MARKETPLACE_PURCHASE -> Icons.Default.ShoppingBag
                else -> Icons.Default.SwapHoriz
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = transaction.amount.toCurrencyString(Currency.USD),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = transactionTypeLabel(transaction.transactionType),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
private fun TransactionDetailsCard(transaction: Transaction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AlwaysWhite)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            DetailRow(
                label = "Descripción",
                value = transaction.description.ifBlank {
                    transactionTypeLabel(transaction.transactionType)
                }
            )
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            DetailRow(
                label = "Fecha",
                value = formatTransactionDate(transaction.createdAt)
            )
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            DetailRow(
                label = "Tipo",
                value = transactionTypeLabel(transaction.transactionType)
            )
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            DetailRow(
                label = "ID de transacción",
                value = transaction.id
            )
        }
    }
}

private fun transactionTypeLabel(type: TransactionType): String = when (type) {
    TransactionType.MARKETPLACE_PURCHASE -> "Compra en marketplace"
    TransactionType.UNKNOWN -> "Transacción"
    else -> {
        "Transacción"
    }
}

private fun formatTransactionDate(createdAt: Long): String {
    // Asumiendo epoch millis. Si createdAt viene como 0 (sin fecha real), muestra un fallback.
    if (createdAt <= 0L) return "Sin fecha"
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(createdAt))
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = androidx.compose.ui.text.style.TextAlign.End,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TransactionDetailScreenContentPreview() {
    TransactionDetailScreenContent(
        transaction = Transaction(
            id = "2d72fd48-8939-4331-985a-f9c23dde1f4c",
            amount = 820,
            createdAt = System.currentTimeMillis(),
            description = "",
            transactionType = TransactionType.MARKETPLACE_PURCHASE
        )
    )
}