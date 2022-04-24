package io.hussienfahmy.mefab_compose.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import io.hussienfahmy.mefab_compose.enums.Position

/**
 * @param position the relative position you want the fab to be in
 * @param mefabSize the size of the composable
 *
 * @return the offset value required to make the composable at this relative position
 */
internal fun generateOffSetTo(position: Position, mefabSize: Dp): IntOffset =
    when (position) {
        Position.TOP_LEFT -> offsetTotTopLeft(mefabSize)
        Position.TOP_CENTER -> offsetToTopCenter(mefabSize)
        Position.TOP_RIGHT -> offsetToTopRight(mefabSize)
        Position.CENTER_LEFT -> offsetToCenterLeft(mefabSize)
        Position.CENTER -> offsetToCenter()
        Position.CENTER_RIGHT -> offsetToCenterRight(mefabSize)
        Position.BOTTOM_LEFT -> offsetToBottomLeft(mefabSize)
        Position.BOTTOM_CENTER -> offsetToBottomCenter(mefabSize)
        Position.BOTTOM_RIGHT -> offsetToBottomRight(mefabSize)
    }

private fun offsetTotTopLeft(mefabSize: Dp) =
    IntOffset(
        -(mefabSize.value).toInt(),
        -(mefabSize.value).toInt()
    )

private fun offsetToTopCenter(mefabSize: Dp) =
    IntOffset(
        0,
        -(mefabSize.value).toInt(),
    )

private fun offsetToTopRight(mefabSize: Dp) =
    IntOffset(
        (mefabSize.value).toInt(),
        -(mefabSize.value).toInt(),
    )

private fun offsetToCenterLeft(mefabSize: Dp) =
    IntOffset(
        -(mefabSize.value).toInt(),
        0
    )

// as the Alignment of the container in Alignment.Center
private fun offsetToCenter() =
    IntOffset(0, 0)

private fun offsetToCenterRight(mefabSize: Dp) =
    IntOffset(
        (mefabSize.value).toInt(),
        0
    )

private fun offsetToBottomLeft(mefabSize: Dp) =
    IntOffset(
        -(mefabSize.value).toInt(),
        (mefabSize.value).toInt(),
    )

private fun offsetToBottomCenter(mefabSize: Dp) =
    IntOffset(
        0,
        (mefabSize.value).toInt(),
    )

private fun offsetToBottomRight(mefabSize: Dp) =
    IntOffset(
        (mefabSize.value).toInt(),
        (mefabSize.value).toInt(),
    )