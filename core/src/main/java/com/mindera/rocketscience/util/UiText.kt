package com.mindera.rocketscience.util

import android.content.Context

sealed class UiText {
    data class DynamicString(val input: String) : UiText()
    data class StringResource(val resId: Int) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> input
            is StringResource -> context.getString(resId)
        }
    }
}