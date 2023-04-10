package com.example.customnotificationskotlin

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat


class NotificationActivity : AppCompatActivity() {
    private var btn1: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        @SuppressLint("WrongConstant") val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(
                this,
                MainActivity::class.java
            ), Intent.FLAG_ACTIVITY_CLEAR_TASK
        )
        btn1 = findViewById(R.id.btn1)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.notifications, null)
        val bitmapDrawable = drawable as BitmapDrawable?
        val largeIcon = bitmapDrawable!!.bitmap
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification

        //Big Picture Style

        val bigPictureStyle = Notification.BigPictureStyle()
            .bigPicture(
                (ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.customnotifications,
                    null
                ) as BitmapDrawable?)!!.bitmap
            )
            .bigLargeIcon(largeIcon)
            .setBigContentTitle("Big Styling")
            .setSummaryText("Image Message")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = Notification.Builder(this)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.notifications)
                .setContentText("New Message")
                .setSubText("Message From Tushar")
                .setChannelId(CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setStyle(bigPictureStyle)
                .setAutoCancel(true)
                .setOngoing(false)
                .build()
            nm.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "New Channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        } else {
            notification = Notification.Builder(this)
                .setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.notifications)
                .setContentText("New Message")
                .setSubText("Message From Tushar")
                .setContentIntent(pendingIntent)
                .setStyle(bigPictureStyle)
                .setAutoCancel(true)
                .setOngoing(false)
                .build()
        }
        btn1?.setOnClickListener(View.OnClickListener { nm.notify(NOTIFICATION_ID, notification) })
    }

    companion object {
        private const val CHANNEL_ID = "My Channel"
        private const val NOTIFICATION_ID = 100
    }
}