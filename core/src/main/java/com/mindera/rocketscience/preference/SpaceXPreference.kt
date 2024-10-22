package com.mindera.rocketscience.preference

interface SpaceXPreference {
    fun persistScrollPosition(position: Int)
    fun getScrollPosition(): Int
}