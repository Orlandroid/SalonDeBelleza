package com.example.citassalon.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavDeepLinkBuilder
import com.example.citassalon.R

class NotificationReceiver : BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = "idappointment"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val args = Bundle()
            args.putString(NOTIFICATION_ID, intent?.getStringExtra(NOTIFICATION_ID))
            pendingIntent(context)
        }
    }


    private fun pendingIntent(context: Context): PendingIntent {
        return NavDeepLinkBuilder(context)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.historialDetailFragment)
            .createPendingIntent()
    }


}