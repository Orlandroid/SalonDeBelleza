package com.example.wallet.transactions

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.core.navigation.wallet.WalletNavigationRoutes
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.base.BaseScreenState
import com.example.core.ui.base.getContentOrNull
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.wallet.components.TransactionsScreenSkeleton
import com.example.core.ui.theme.AlwaysWhite
import com.example.core.ui.theme.Background
import com.example.core.util.toCurrencyString
import com.example.domain.transaction.Transaction
import com.example.domain.transaction.TransactionType
import com.example.domain.wallet.Currency
import com.example.wallet.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun TransactionsScreen(
    navController: NavController,
    viewModel: TransactionViewmodel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsStateWithLifecycle()
    when (viewState) {
        BaseScreenState.OnLoading -> {
            TransactionsScreenSkeleton()
        }

        is BaseScreenState.OnContent -> {

            viewState.getContentOrNull()?.let { state ->
                BaseComposeScreen(
                    toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.transactions)),
                    navController = navController
                ) {
                    TransactionsScreenContent(
                        transactions = state.transactions,
                        onClick = {
                            navController.navigate(WalletNavigationRoutes.TransactionDetail)
                        }
                    )
                }
            }

        }


        is BaseScreenState.OnError -> {
            BaseErrorScreen()
        }
    }

}

@Composable
private fun TransactionsScreenContent(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>,
    onClick: () -> Unit
) {
    if (transactions.isEmpty()) {
        EmptyTransactions(
            modifier = modifier
                .fillMaxSize()
                .background(Background)
        )
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(vertical = 8.dp)
    ) {
        items(
            items = transactions,
            key = { it.id }
        ) { transaction ->
            ItemTransaction(transaction = transaction, onClick = onClick)
        }
    }
}

@Composable
private fun ItemTransaction(transaction: Transaction, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AlwaysWhite),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TransactionIcon(type = transaction.transactionType)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = transaction.description.ifBlank {
                        transactionTypeLabel(transaction.transactionType)
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = formatTransactionDate(transaction.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = transaction.amount.toCurrencyString(Currency.USD),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun TransactionIcon(type: TransactionType) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(color = Color(0xFFEFEFEF), shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val icon = when (type) {
            TransactionType.MARKETPLACE_PURCHASE -> Icons.Default.ShoppingBag
            TransactionType.UNKNOWN -> Icons.Default.SwapHoriz
            else -> {
                Icons.Default.SwapHoriz
            }
        }
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
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
private fun EmptyTransactions(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.SwapHoriz,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.not_transactions),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun TransactionsScreenContentPreview() {
    TransactionsScreenContent(
        transactions = listOf(
            Transaction(),
            Transaction(),
        ),
        onClick = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun TransactionsScreenContentEmptyPreview() {
    TransactionsScreenContent(transactions = emptyList(), onClick = {})
}