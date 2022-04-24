package io.hussienfahmy.mefab_compose.utils

import io.hussienfahmy.mefab_compose.enums.Position


/**
 * @return the suitable positions for all of EdgeFab based on how many of them
 */
internal fun getFabsSuitablePositions(containerPosition: Position, fabsSize: Int): List<Position> {
    return when (containerPosition) {
        Position.TOP_LEFT -> suitablePositionsForChildrenInTopLeft(fabsSize)
        Position.TOP_CENTER -> suitablePositionsForChildrenInTopCenter(fabsSize)
        Position.TOP_RIGHT -> suitablePositionsForChildrenInTopRight(fabsSize)
        Position.CENTER_LEFT -> suitablePositionsForChildrenInCenterLeft(fabsSize)
        Position.CENTER -> suitablePositionsForChildrenInCenter(fabsSize)
        Position.CENTER_RIGHT -> suitablePositionsForChildrenInCenterRight(fabsSize)
        Position.BOTTOM_RIGHT -> suitablePositionsForChildrenInBottomRight(fabsSize)
        Position.BOTTOM_CENTER -> suitablePositionsForChildrenInBottomCenter(fabsSize)
        Position.BOTTOM_LEFT -> suitablePositionsForChildrenInBottomLeft(fabsSize)
    }
}

private fun suitablePositionsForChildrenInTopLeft(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.BOTTOM_RIGHT)
        2 -> listOf(Position.CENTER_RIGHT, Position.BOTTOM_CENTER)
        3 -> listOf(Position.CENTER_RIGHT, Position.BOTTOM_RIGHT, Position.BOTTOM_CENTER)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInTopCenter(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.BOTTOM_CENTER)
        2 -> listOf(Position.BOTTOM_RIGHT, Position.BOTTOM_LEFT)
        3 -> listOf(Position.CENTER_RIGHT, Position.BOTTOM_CENTER, Position.CENTER_LEFT)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInTopRight(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.BOTTOM_LEFT)
        2 -> listOf(Position.BOTTOM_CENTER, Position.CENTER_LEFT)
        3 -> listOf(Position.BOTTOM_CENTER, Position.BOTTOM_LEFT, Position.CENTER_LEFT)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInCenterLeft(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.CENTER_RIGHT)
        2 -> listOf(Position.TOP_RIGHT, Position.BOTTOM_RIGHT)
        3 -> listOf(Position.TOP_CENTER, Position.CENTER_RIGHT, Position.BOTTOM_CENTER)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInCenter(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.TOP_CENTER)
        2 -> listOf(Position.TOP_LEFT, Position.TOP_RIGHT)
        3 -> listOf(Position.TOP_CENTER, Position.BOTTOM_RIGHT, Position.BOTTOM_LEFT)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInCenterRight(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.CENTER_LEFT)
        2 -> listOf(Position.BOTTOM_LEFT, Position.TOP_LEFT)
        3 -> listOf(Position.BOTTOM_CENTER, Position.CENTER_LEFT, Position.TOP_CENTER)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInBottomLeft(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.TOP_RIGHT)
        2 -> listOf(Position.TOP_CENTER, Position.CENTER_RIGHT)
        3 -> listOf(Position.TOP_CENTER, Position.TOP_RIGHT, Position.CENTER_RIGHT)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInBottomCenter(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.TOP_CENTER)
        2 -> listOf(Position.TOP_LEFT, Position.TOP_RIGHT)
        3 -> listOf(Position.CENTER_LEFT, Position.TOP_CENTER, Position.CENTER_RIGHT)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun suitablePositionsForChildrenInBottomRight(fabSize: Int): List<Position> {
    return when (fabSize) {
        1 -> listOf(Position.TOP_LEFT)
        2 -> listOf(Position.CENTER_LEFT, Position.TOP_CENTER)
        3 -> listOf(Position.CENTER_LEFT, Position.TOP_LEFT, Position.TOP_CENTER)
        else -> throwNumberOfItemsItemsException(fabSize)
    }
}

private fun throwNumberOfItemsItemsException(size: Int): Nothing {
    throw IllegalStateException("Max items is 3 and Min is 1, the current is $size")
}