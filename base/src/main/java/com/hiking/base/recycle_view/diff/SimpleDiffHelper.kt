package com.kronos.diffutil

import java.util.concurrent.CopyOnWriteArrayList

/**
 * https://github.com/Leifzhang/DiffUtils
 */
class SimpleDiffHelper<T> : BaseDiffHelper<T>() {

    override fun clone() {
        try {
            itemsCursor?.apply {
                mData = CopyOnWriteArrayList(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}