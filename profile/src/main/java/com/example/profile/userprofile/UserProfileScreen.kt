package com.example.profile.userprofile

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.core.navigation.AppNavigationRoutes
import com.example.core.ui.base.BaseComposeScreen
import com.example.core.ui.base.BaseScreenState
import com.example.core.ui.base.getContentOrNull
import com.example.core.ui.components.BaseErrorScreen
import com.example.core.ui.components.ToolbarConfiguration
import com.example.core.ui.components.skeletons.UserProfileScreenSkeleton
import com.example.core.ui.theme.Background
import com.example.core.util.toCurrencyString
import com.example.core.util.uriToBitmap
import com.example.domain.loyalty.Loyalty
import com.example.domain.loyalty.LoyaltyTier
import com.example.domain.loyalty.PromotionCode
import com.example.domain.wallet.Currency
import com.example.profile.R


@Composable
fun UserProfileScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val userProfileViewModel: UserProfileViewModel = hiltViewModel()
    val uiState = userProfileViewModel.state.collectAsStateWithLifecycle()
    val imageUserRemote = remember { mutableStateOf<Bitmap?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), onResult = { uri ->
            uri?.let {
                val imageBitmap = context.uriToBitmap(uri)
                // userProfileViewModel.saveImageUser(...)
            }
        }
    )
    BaseComposeScreen(
        navController = navController, toolbarConfiguration = ToolbarConfiguration(
            showToolbar = true, title = stringResource(id = R.string.userProfile)
        )
    ) {
        when (uiState.value) {
            BaseScreenState.OnLoading -> {
                UserProfileScreenSkeleton()
            }

            is BaseScreenState.OnContent -> {
                uiState.value.getContentOrNull()?.let { state ->
                    UserProfileScreenContent(
                        imageUserRemote = imageUserRemote.value,
                        launchGallery = { galleryLauncher.launch("image/*") },
                        userProfileState = state,
                        navigateToLoyalty = { navController.navigate(AppNavigationRoutes.LoyaltyNavigationRoute) }
                    )
                }
            }

            is BaseScreenState.OnError -> {
                BaseErrorScreen()
            }
        }
    }
}


@Composable
private fun UserProfileScreenContent(
    modifier: Modifier = Modifier,
    imageUserRemote: Bitmap? = null,
    launchGallery: () -> Unit,
    navigateToLoyalty: () -> Unit,
    userProfileState: UserProfileUiState
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        if (imageUserRemote == null) {
            ImageUser(model = R.drawable.userprofile) {
                launchGallery.invoke()
            }
        } else {
            ImageUser(model = imageUserRemote.asImageBitmap()) {
                launchGallery.invoke()
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = userProfileState.name ?: stringResource(R.string.name_user),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            CircleStatus(statusColor = Color.Green)
        }

        Spacer(modifier = Modifier.height(24.dp))


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            BaseLabel(key = stringResource(R.string.name), value = userProfileState.name)
            HorizontalDivider(
                Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
            BaseLabel(key = stringResource(R.string.phone), value = userProfileState.phone)
            HorizontalDivider(
                Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
            BaseLabel(key = stringResource(R.string.label_email), value = userProfileState.email)
            HorizontalDivider(
                Modifier.padding(horizontal = 16.dp),
                thickness = 0.5.dp,
                color = Color.LightGray
            )
            BaseLabel(
                key = stringResource(R.string.money),
                value = userProfileState.money?.toCurrencyString(Currency.USD)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        userProfileState.loyalty?.let { loyalty ->
            LoyaltySection(loyalty = loyalty, onRedeem = navigateToLoyalty)
        }

        if (userProfileState.coupons.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            CouponsSection(coupons = userProfileState.coupons)
        }
    }
}

@Composable
private fun LoyaltySection(
    loyalty: Loyalty,
    onRedeem: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onRedeem
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "My Loyalty",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "${loyalty.tier} Member",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Icon(
                    imageVector = Icons.Outlined.Stars,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = when (loyalty.tier) {
                        LoyaltyTier.GOLD -> Color(0xFFFFD700)
                        LoyaltyTier.SILVER -> Color(0xFFC0C0C0)
                        else -> Color(0xFFCD7F32)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${loyalty.balance} ⭐",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            val nextTier = when (loyalty.tier) {
                LoyaltyTier.BRONZE -> LoyaltyTier.SILVER
                LoyaltyTier.SILVER -> LoyaltyTier.GOLD
                else -> null
            }

            if (nextTier != null) {
                val progress = loyalty.lifetimePoints.toFloat() / nextTier.minPoints.toFloat()
                val pointsNeeded = nextTier.minPoints - loyalty.lifetimePoints

                Column {
                    LinearProgressIndicator(
                        progress = { progress.coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = Color.LightGray.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$pointsNeeded points until ${
                            nextTier.name.lowercase().replaceFirstChar { it.uppercase() }
                        }",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun CouponsSection(coupons: List<PromotionCode>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.my_cupons),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            contentPadding = PaddingValues(end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(coupons) { coupon ->
                CouponItem(coupon = coupon)
            }
        }
    }
}

@Composable
private fun CouponItem(coupon: PromotionCode) {
    Card(
        modifier = Modifier
            .width(180.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.ConfirmationNumber,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${coupon.discountPercentage}% OFF",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = coupon.code,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun BaseLabel(
    key: String,
    value: String?
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = key,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value.orEmpty(),
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}


@Composable
private fun ImageUser(
    model: Any,
    launchGallery: () -> Unit
) {
    val imageModifier =
        Modifier
            .size(144.dp)
            .clip(CircleShape)
            .border(2.dp, Color.LightGray, CircleShape)
            .clickable {
                launchGallery.invoke()
            }
    if (model is ImageBitmap) {
        Image(
            contentScale = ContentScale.Crop,
            bitmap = model,
            contentDescription = "ImageProfile",
            modifier = imageModifier
        )
    } else {
        AsyncImage(
            contentScale = ContentScale.Crop,
            model = model,
            contentDescription = "ImageProfile",
            modifier = imageModifier
        )
    }
}

@Composable
private fun CircleStatus(statusColor: Color) {
    Canvas(modifier = Modifier.size(14.dp)) {
        drawCircle(color = statusColor)
    }
}


@Composable
@Preview(showBackground = true)
private fun UserProfileScreenContentPreview() {
    UserProfileScreenContent(
        userProfileState = UserProfileUiState(
            name = "Maria",
            phone = "1234567890",
            email = "maria@email.com",
            money = 500L,
            loyalty = Loyalty(balance = 300, lifetimePoints = 300, tier = LoyaltyTier.BRONZE),
            coupons = listOf(
                PromotionCode(code = "SALON-123", discountPercentage = 10),
                PromotionCode(code = "SALON-ABC", discountPercentage = 20)
            )
        ),
        launchGallery = {},
        navigateToLoyalty = {},
    )
}
