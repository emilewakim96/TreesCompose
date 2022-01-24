package com.example.treescompose.util

import android.content.res.Resources

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

/* format string (to safely pass json object string in compose route) */
fun String.formatString(): String {
    return this.replace('/', '|', ignoreCase = true)
}

fun String.revertFormattedString(): String {
    return this.replace('|', '/', ignoreCase = true)
}