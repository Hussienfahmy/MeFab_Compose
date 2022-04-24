package io.hussienfahmy.mefab_compose

import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.hussienfahmy.mefab_compose.data.FabData
import io.hussienfahmy.mefab_compose.enums.Position
import io.hussienfahmy.mefab_compose.enums.State
import io.hussienfahmy.mefab_compose.utils.generateOffSetTo
import io.hussienfahmy.mefab_compose.utils.getFabsSuitablePositions
import kotlin.math.roundToInt

/**
 * Me fab. Stateful Composable
 *
 * @param centerFabData Data of the center fab
 * @param fab1Data Data of the first fab
 * @param fab2Data Data of the second fab
 * @param fab3Data Data of the third fab
 * @param closeAfterEdgeClick if true the state will be [State.CLOSED] after click on any edge fab
 */
@Composable
public fun MeFab(
    centerFabData: FabData = FabData({}, { Icon(Icons.Filled.Add, "Central Fab") }),
    fab1Data: FabData? = null,
    fab2Data: FabData? = null,
    fab3Data: FabData? = null,
    closeAfterEdgeClick: Boolean = true
) {
    // to control the x and y of the [MeFab] when the center fab dragged
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // indicate the state of the [MeFab]
    var state by remember { mutableStateOf(State.CLOSED) }

    // indicate where [MeFab] is located relative to the screen e.g TOP_LEFT, BOTTOM_RIGHT
    var screenRelativePosition by remember { mutableStateOf(Position.default) }

    // list of all the edgeFabs passed
    val edgeFabsData = listOf(fab1Data, fab2Data, fab3Data)
    val edgeFabsCount = edgeFabsData.count { it != null }

    // list of positions to indicate where each edgeFab should located
    val edgeFabsPositions = when (state) {
        State.EXPANDED -> getFabsSuitablePositions(screenRelativePosition, edgeFabsCount)
        State.CLOSED -> List(3) { Position.CENTER }
    }

    val mefabSize = 200.dp
    Box(
        modifier = Modifier
            .size(mefabSize)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) },
        contentAlignment = Alignment.Center
    ) {
        val transition = updateTransition(mefabSize, label = "MeFab Transition")

        edgeFabsData.forEachIndexed { index, fabData ->
            if (fabData != null) {
                val offset by transition.animateIntOffset(label = "Edge Moving") {
                    generateOffSetTo(edgeFabsPositions[index], it)
                }

                EdgeFab(
                    onClick = {
                        fabData.onClick()
                        if (closeAfterEdgeClick) state = state.inverse()
                    },
                    content = fabData.content,
                    state = state,
                    modifier = Modifier.offset { offset }
                )
            }
        }

        // must be at the bottom to make it the most higher elevation
        CentralFab(
            state = state,
            content = centerFabData.content,
            onClick = {
                state = state.inverse()
                centerFabData.onClick()
            },
            onDrag = { offset ->
                offsetX += offset.x
                offsetY += offset.y
            },
            onPositionChange = { position ->
                screenRelativePosition = position
            }
        )
    }
}