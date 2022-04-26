package io.hussienfahmy.mefab_compose

import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.hussienfahmy.mefab_compose.enums.Position
import io.hussienfahmy.mefab_compose.enums.State
import io.hussienfahmy.mefab_compose.utils.Separators
import io.hussienfahmy.mefab_compose.utils.generateOffSetTo
import io.hussienfahmy.mefab_compose.utils.generateSeparators
import io.hussienfahmy.mefab_compose.utils.getFabsSuitablePositions
import kotlin.math.roundToInt

@Composable
public fun MeFab(
    state: State,
    centralFab: @Composable () -> Unit,
    fab1: @Composable (() -> Unit)? = null,
    fab2: @Composable (() -> Unit)? = null,
    fab3: @Composable (() -> Unit)? = null,
) {
    // the value of x and y of borders separates each grid of the screen
    val separators = LocalContext.current.resources.displayMetrics.generateSeparators()

    // the size of the MeFab
    val mefabSizeDp = 200.dp

    // to control the x and y of the [MeFab] when the center fab dragged
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    // indicate where [MeFab] is located relative to the screen e.g TOP_LEFT, BOTTOM_RIGHT
    var screenRelativePosition by remember { mutableStateOf(Position.default) }

    // list of all the edgeFabs passed
    val edgeFabs = listOf(fab1, fab2, fab3)
    val edgeFabsCount = edgeFabs.count { it != null }

    // list of positions to indicate where each edgeFab should located
    val edgeFabsPositions = when (state) {
        State.EXPANDED -> getFabsSuitablePositions(screenRelativePosition, edgeFabsCount)
        State.CLOSED -> List(3) { Position.CENTER }
    }

    Box(
        modifier = Modifier
            .size(mefabSizeDp)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .onGloballyPositioned {
                // report the position of the center fab relative to the screen
                screenRelativePosition =
                    getMeFabPositionOnScreen(it.size, it.positionInWindow(), separators)
            },
        contentAlignment = Alignment.Center
    ) {
        val transition = updateTransition(mefabSizeDp, label = "MeFab Transition")

        edgeFabs.forEachIndexed { index, edgeFab ->
            if (edgeFab != null) {
                val offset by transition.animateIntOffset(label = "Edge Moving") {
                    generateOffSetTo(edgeFabsPositions[index], it)
                }

                Box(Modifier.offset { offset }) {
                    edgeFab()
                }
            }
        }

        // must be at the bottom to make it the most higher elevation
        Box(
            Modifier.pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
        ) {
            centralFab()
        }
    }
}

@Composable
public fun rememberMeFabState(): MutableState<State> = rememberSaveable {
    mutableStateOf(State.CLOSED)
}

/**
 * @param separators the separators of each grid on the screen
 *
 * @return the [Position] relative to the screen
 */
private fun getMeFabPositionOnScreen(
    meFabSize: IntSize,
    meFabCoordinates: Offset,
    separators: Separators
): Position {
    // get the x, y of the center of the fab
    val x = (meFabCoordinates.x + meFabSize.width / 2).toInt()
        .coerceIn(0..separators.screenWidth)

    val y = (meFabCoordinates.y + meFabSize.height / 2).toInt()
        .coerceIn(0..separators.screenHeight)

    return when {
        y in separators.borderToY1Rang && x in separators.borderToX1Rang -> Position.TOP_LEFT
        y in separators.borderToY1Rang && x in separators.x1ToX2Range -> Position.TOP_CENTER
        y in separators.borderToY1Rang && x in separators.x2ToBorderRange -> Position.TOP_RIGHT

        y in separators.y1ToY2Range && x in separators.borderToX1Rang -> Position.CENTER_LEFT
        y in separators.y1ToY2Range && x in separators.x1ToX2Range -> Position.CENTER
        y in separators.y1ToY2Range && x in separators.x2ToBorderRange -> Position.CENTER_RIGHT

        y in separators.y2ToBorderRange && x in separators.borderToX1Rang -> Position.BOTTOM_LEFT
        y in separators.y2ToBorderRange && x in separators.x1ToX2Range -> Position.BOTTOM_CENTER
        y in separators.y2ToBorderRange && x in separators.x2ToBorderRange -> Position.BOTTOM_RIGHT
        else -> throw IllegalStateException("Can't determine the position for provided X = $x, Y = $y")
    }
}