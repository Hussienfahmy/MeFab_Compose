package io.hussienfahmy.mefab_compose.enums

/**
 * Position.
 * Represent a relative position to the screen
 */
internal enum class Position {
    TOP_LEFT,
    TOP_CENTER,
    TOP_RIGHT,
    CENTER_LEFT,
    CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_CENTER,
    BOTTOM_RIGHT;

    companion object {
        val default
         get() = BOTTOM_RIGHT
    }
}