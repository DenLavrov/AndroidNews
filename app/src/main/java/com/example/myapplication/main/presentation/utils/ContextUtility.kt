package com.example.myapplication.main.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openUri(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent, null)
}