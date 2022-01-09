package com.qzeng.focustask.initializer

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import com.bumptech.glide.Glide
import com.qzeng.focustask.utils.AppLogger

private const val TAG = "ImageLoaderInitializer"

class ImageLoaderInitializer : Initializer<Glide> {
    override fun create(context: Context): Glide {
        Log.d(TAG, "ImageLoaderInitializer inited...")
        return Glide.get(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf(AppLoggerInitializer::class.java)
    }
}