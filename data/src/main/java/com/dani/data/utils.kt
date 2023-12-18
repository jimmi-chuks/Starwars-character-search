package com.dani.data

import android.net.Uri

fun getLastPath(url: String): String? {
    val trimmed = url.trim { it == '/' }
    val uri: Uri = Uri.parse(trimmed)
    return uri.lastPathSegment
}
