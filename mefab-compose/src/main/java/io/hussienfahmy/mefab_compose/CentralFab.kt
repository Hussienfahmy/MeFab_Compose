package io.hussienfahmy.mefab_compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import io.hussienfahmy.mefab_compose.enums.State

/**
 * Represent the fab that will be always in the center
 *
 * @param state the state of [MeFab]
 * @param content the content that will be inside the central fab
 */
@Composable
public fun CentralFab(
    state: State,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // rotation of the central fab
    val rotation by animateFloatAsState(
        targetValue = when (state) {
            State.EXPANDED -> 315f
            State.CLOSED -> 0f
        }
    )

    // as the rotation invalidate the values of the drag so rotate the box instead the fab
    FloatingActionButton(
        content = {
            Box(
                modifier = Modifier.rotate(rotation)
            ) { content() }
        },
        onClick = {
            onClick()
        },
        modifier = modifier
    )
}