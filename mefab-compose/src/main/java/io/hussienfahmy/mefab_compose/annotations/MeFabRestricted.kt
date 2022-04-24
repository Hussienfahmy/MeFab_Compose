package io.hussienfahmy.mefab_compose.annotations

/**
 * Me fab restricted.
 * prevent user from relying on any of the library internal implementation
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This is internal API for MeFab-Compose library, please don't rely on it. :)"
)
public annotation class MeFabRestricted