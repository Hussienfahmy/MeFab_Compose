package io.hussienfahmy.mefab_compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import io.hussienfahmy.mefab_compose.enums.State

/**
 * Represent the fab that will be at the edge
 * @param state represent the state of the [MeFab]
 * @param content content that will be inside the [FloatingActionButton]
 */
@Composable
internal fun EdgeFab(
    onClick: () -> Unit,
    modifier: Modifier,
    state: State,
    content: @Composable () -> Unit
) {
    val scale by animateFloatAsState(targetValue = when(state) {
        State.EXPANDED -> 1f
        State.CLOSED -> 0f
    })

    FloatingActionButton(
        onClick = onClick,
        content = content,
        modifier = modifier.then(
            Modifier
                .size(40.dp)
                .scale(scale)
        )
    )
}