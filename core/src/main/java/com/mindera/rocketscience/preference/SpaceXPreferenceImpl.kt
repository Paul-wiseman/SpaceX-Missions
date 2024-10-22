package com.mindera.rocketscience.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SpaceXPreferenceImpl(
    context: Context
) : SpaceXPreference {
    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    override fun persistScrollPosition(position: Int) {
        edit { putInt(SCROLL_STATE_KEY, position) }
    }

    override fun getScrollPosition(): Int = sharedPreferences.getInt(SCROLL_STATE_KEY, 0)

    private fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(sharedPreferences.edit()) {
            block()
            commit()
        }
    }

    private companion object {
        const val SHARED_PREFERENCES_NAME = "spacex_preference"
        const val SCROLL_STATE_KEY = "scroll_state_key"
    }
}