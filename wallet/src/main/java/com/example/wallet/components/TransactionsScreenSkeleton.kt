package com.example.wallet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.ui.components.shimmerBrush
import com.example.core.ui.theme.AlwaysWhite
import com.example.core.util.toCurrencyString
import com.example.domain.transaction.Transaction
import com.example.domain.wallet.Currency


@Composable
fun TransactionsScreenSkeleton(
) {
    LazyColumn {
        for (x in 0..5) {
            item {
                TransactionSkeletonItem(Transaction())
            }
        }

    }
}

@Composable
fun TransactionSkeletonItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AlwaysWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TransactionIcon()
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    text = "transaction.description",
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Transparent
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    text = "transaction.createdAt.toString()",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Transparent
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.background(shimmerBrush()),
                color = Color.Transparent,
                text = transaction.amount.toCurrencyString(Currency.USD),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun TransactionIcon() {
    Box(
        modifier = Modifier
            .size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .size(20.dp)
                .background(shimmerBrush())
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun TransactionsScreenSkeletonPreview() {
    TransactionsScreenSkeleton()
}