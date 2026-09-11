package com.example.loyalty.rewards


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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.theme.Background
import com.example.domain.loyalty.Reward
import com.example.loyalty.R

@Composable
fun RewardsScreen(
    navController: NavHostController,
    viewModel: RewardsViewModel = hiltViewModel()
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()

    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true,
            title = stringResource(R.string.redeem_rewards)
        )
    ) {
        RewardsScreenContent(
            modifier = Modifier,
            userBalance = uiState.value.userBalance,
            rewards = uiState.value.rewards,
            onRedeem = {
                viewModel.onEvents(RewardsEvents.OnRedeemReward(it))
            }
        )
    }
}


@Composable
private fun RewardsScreenContent(
    modifier: Modifier = Modifier,
    userBalance: Int,
    rewards: List<Reward>,
    onRedeem: (reward: Reward) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {

        PointsHeader(balance = userBalance)


        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.available_rewards),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(rewards) { reward ->
                RewardItem(
                    reward = reward,
                    canAfford = userBalance >= reward.pointsRequired,
                    onRedeem = { onRedeem.invoke(reward) }
                )
            }
        }
    }
}

@Composable
private fun PointsHeader(balance: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.your_current_balance),
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
                Text(
                    "$balance ⭐",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black
                )
            }
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.3f),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
private fun RewardItem(
    reward: Reward,
    canAfford: Boolean,
    onRedeem: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.ConfirmationNumber,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reward.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = stringResource(
                        R.string.points_required,
                        reward.pointsRequired
                    ),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            Button(
                onClick = onRedeem,
                enabled = canAfford,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.LightGray
                )
            ) {
                Text(
                    text =
                        if (canAfford)
                            stringResource(R.string.redeem)
                        else
                            stringResource(R.string.missing_points)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RewardsScreenContentPreviewSuccess() {
    val reward = Reward(
        name = "Cupon de descuento",
        pointsRequired = 0,
        discountPercentage = 10
    )
    RewardsScreenContent(
        modifier = Modifier,
        userBalance = 300,
        rewards = listOf(reward, reward, reward),
        onRedeem = {}
    )
}