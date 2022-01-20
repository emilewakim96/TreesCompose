package com.example.treescompose.util

import android.content.res.Resources

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}