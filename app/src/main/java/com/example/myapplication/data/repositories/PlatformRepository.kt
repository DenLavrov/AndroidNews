package com.example.myapplication.data.repositories

import android.content.Context
import android.content.Intent
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface PlatformRepository{
    fun openUrl(url: String)
}

class PlatformRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context):
    PlatformRepository {
    override fun openUrl(url: String) {
        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent, null)
    }
}