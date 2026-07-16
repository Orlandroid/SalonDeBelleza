package com.example.citassalon.presentacion.features.profile.contact_us


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.citassalon.R
import com.example.citassalon.presentacion.features.base.BaseComposeScreen
import com.example.citassalon.presentacion.features.components.ToolbarConfiguration
import com.example.citassalon.presentacion.features.theme.Background

@Composable
fun ContactUsScreen(
    navController: NavHostController
) {
    BaseComposeScreen(
        navController = navController,
        toolbarConfiguration = ToolbarConfiguration(title = stringResource(R.string.contact_us))
    ) {
        ContactUsScreenContent()
    }
}

@Composable
private fun ContactUsScreenContent(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = stringResource(R.string.contact_us_welcome),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(R.string.contact_us_subtitle),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                color = Color.Red
            )
        }

        item {

            ContactCard(
                icon = Icons.Default.Call,
                title = stringResource(R.string.contact_phone),
                value = "+1 (555) 123-4567",
                actionLabel = stringResource(R.string.call_now),
                onAction = { phone -> callPhone(phone) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            ContactCard(
                icon = Icons.Default.Email,
                title = stringResource(R.string.contact_email),
                value = "info@salondelabelleza.com",
                actionLabel = stringResource(R.string.send_email),
                onAction = { email -> sendEmail(email) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {

            ContactCard(
                icon = Icons.Default.LocationOn,
                title = stringResource(R.string.contact_location),
                value = "123 Beauty Street, Fashion City, FC 12345",
                actionLabel = stringResource(R.string.open_map),
                onAction = { }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            BusinessHoursSection()

            Spacer(modifier = Modifier.height(24.dp))

            FollowUsSection()

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ContactCard(
    icon: ImageVector,
    title: String,
    value: String,
    actionLabel: String,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.Red,
                modifier = Modifier.width(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))


            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = value,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }


            IconButton(
                onClick = { onAction(value) },
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = actionLabel,
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
private fun BusinessHoursSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.business_hours),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            BusinessHourRow("Monday - Friday", "9:00 AM - 8:00 PM")
            Spacer(modifier = Modifier.height(8.dp))
            BusinessHourRow("Saturday", "10:00 AM - 6:00 PM")
            Spacer(modifier = Modifier.height(8.dp))
            BusinessHourRow("Sunday", "11:00 AM - 5:00 PM")
        }
    }
}

@Composable
private fun BusinessHourRow(
    day: String,
    hours: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(
            text = day,
            fontSize = 12.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = hours,
            fontSize = 12.sp,
            color = Color.Red,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun FollowUsSection(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.follow_us),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
            ) {
                SocialMediaButton(label = "Facebook", onClick = { })
                Spacer(modifier = Modifier.width(8.dp))
                SocialMediaButton(label = "Instagram", onClick = { })
                Spacer(modifier = Modifier.width(8.dp))
                SocialMediaButton(label = "Twitter", onClick = { })
            }
        }
    }
}

@Composable
private fun SocialMediaButton(
    label: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

private fun callPhone(phone: String) {
    //Todo add action to call to the phone
}

private fun sendEmail(email: String) {
    //Todo add action to send one email
}

@Composable
@Preview(showBackground = true)
private fun ContactUsScreenContentPreview() {
    ContactUsScreenContent()
}
