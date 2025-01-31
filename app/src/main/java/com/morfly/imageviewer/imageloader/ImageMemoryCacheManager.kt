package com.morfly.imageviewer.imageloader

import android.graphics.Bitmap
import android.util.LruCache


interface ImageMemoryCacheManager {

    fun saveToMemoryCache(key: String, bitmap: Bitmap)

    fun getFromMemoryCache(key: String): Bitmap?
}


class ImageMemoryCacheManagerDefault : ImageMemoryCacheManager {

    private val memoryCache: LruCache<String, Bitmap>

    init {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    override fun saveToMemoryCache(key: String, bitmap: Bitmap) {
        if (getFromMemoryCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    override fun getFromMemoryCache(key: String): Bitmap? {
        return memoryCache.get(key);
    }
}