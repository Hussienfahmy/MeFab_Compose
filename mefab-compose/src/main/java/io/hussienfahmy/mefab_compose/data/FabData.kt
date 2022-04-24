package io.hussienfahmy.mefab_compose.data

import androidx.compose.runtime.Composable

/**
 * FabData.
 * Describe the any of EdgeFab or CentralFab
 * @param onClick execute when clicks on the fab
 * @param content the content inside the fabe itself
 */
public class FabData(
    public val onClick: () -> Unit = {},
    public val content: @Composable () -> Unit
)