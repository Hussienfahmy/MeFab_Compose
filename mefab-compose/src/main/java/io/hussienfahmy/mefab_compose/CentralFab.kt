package io.hussienfahmy.mefab_compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import io.hussienfahmy.mefab_compose.enums.Position
import io.hussienfahmy.mefab_compose.enums.State
import io.hussienfahmy.mefab_compose.utils.Separators
import io.hussienfahmy.mefab_compose.utils.generateSeparators

/**
 * Represent the fab that will be always in the center
 *
 * @param state the state of [MeFab]
 * @param onDrag report an offset when dragging
 * @param onPositionChange report its new position relative to screen when dragging
 * @param content the content that will be inside the central fab
 */
@Composable
internal fun CentralFab(
    state: State,
    onClick: () -> Unit,
    onDrag: (dragAmount: Offset) -> Unit,
    onPositionChange: (position: Position) -> Unit,
    content: @Composable () -> Unit
) {
    // the value of x and y of borders separates each grid of the screen
    val separators = LocalContext.current.resources.displayMetrics.generateSeparators()

    // rotation of the central fab
    val rotation by animateFloatAsState(
        targetValue = when (state) {
            State.EXPANDED -> 315f
            State.CLOSED -> 0f
        }
    )

    // the size of the CentralFab
    var size by remember { mutableStateOf(IntSize.Zero) }
    // the global Coordinates or called offset of the central fab on the screen
    var coordinates by remember { mutableStateOf(Offset.Zero) }

    // report the position of the center fab relative to the screen at the first time
    onPositionChange(getCentralFabPositionOnScreen(size, coordinates, separators))

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
        modifier = Modifier
            .onGloballyPositioned {
                size = it.size
                coordinates = it.positionInWindow()
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    onDrag(dragAmount)
                }
            }
    )
}

/**
 *
 * @param centralFabSize the size of the fab
 * @param centralFabCoordinates the coordinates (offset) of the fab
 * @param separators the separators of each grid on the screen
 *
 * @return the [Position] relative to the screen
 */
private fun getCentralFabPositionOnScreen(
    centralFabSize: IntSize,
    centralFabCoordinates: Offset,
    separators: Separators
): Position {
    // get the x, y of the center of the fab
    val x = (centralFabCoordinates.x + centralFabSize.width / 2).toInt()
        .coerceIn(0..separators.screenWidth)

    val y = (centralFabCoordinates.y + centralFabSize.height / 2).toInt()
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