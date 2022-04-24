package io.hussienfahmy.mefab_compose.utils

import android.util.DisplayMetrics

/**
 * Separators.
 * defining the borders separate each square on the screen
 *      |   |   |
 *      --------- y1
 *      |   |   |
 *      --------- y2
 *      |   |   |
 *       x1   x2
 * @property borderToX1Rang range from the left border to the x1
 * @property x1ToX2Range range from x1 to x2
 * @property x2ToBorderRange
 * @property borderToY1Rang
 * @property y1ToY2Range
 * @property y2ToBorderRange
 */
internal class Separators constructor(
    val borderToX1Rang: IntRange,
    val x1ToX2Range: IntRange,
    val x2ToBorderRange: IntRange,
    val borderToY1Rang: IntRange,
    val y1ToY2Range: IntRange,
    val y2ToBorderRange: IntRange,
    val screenWidth: Int,
    val screenHeight: Int
)

internal fun DisplayMetrics.generateSeparators(): Separators {
    val y1 = heightPixels / 3
    val y2 = y1 * 2
    val x1 = widthPixels / 3
    val x2 = x1 * 2

    return Separators(
        borderToX1Rang = 0..x1,
        x1ToX2Range = x1..x2,
        x2ToBorderRange = x2..widthPixels,
        borderToY1Rang = 0..y1,
        y1ToY2Range = y1..y2,
        y2ToBorderRange = y2..heightPixels,
        screenWidth = widthPixels,
        screenHeight = heightPixels
    )
}