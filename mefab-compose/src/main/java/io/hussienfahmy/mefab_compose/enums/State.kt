package io.hussienfahmy.mefab_compose.enums

/**
 * State.
 * Represent a state of the MeFab Expanded or Closed
 */
public enum class State {
    EXPANDED,
    CLOSED;

    /**
     * Inverse.
     *
     * @return [State] inverted if [EXPANDED] return [CLOSED] and vice versa
     */
    public fun inverse(): State = when (this) {
        EXPANDED -> CLOSED
        CLOSED -> EXPANDED
    }
}