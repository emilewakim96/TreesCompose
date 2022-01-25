package com.example.treescompose.util

import android.content.res.Resources
import androidx.compose.runtime.snapshots.SnapshotStateList

fun Int.pxToDp(): Int {
    return (this / Resources.getSystem().displayMetrics.density).toInt()
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}