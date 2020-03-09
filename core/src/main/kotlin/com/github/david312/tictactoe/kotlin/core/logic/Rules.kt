package com.github.david312.tictactoe.kotlin.core.logic

import com.github.david312.tictactoe.kotlin.core.domain.*

/**
 * Enum class with the rules a board must respect when changing a tile value.
 */
enum class BoardRules(private val rule: (Board, Int, Int, TileValue) -> Boolean) {
    /**
     * A tile that is not empty cannot be overwritten.
     */
    CANNOT_OVERWRITE_NON_EMPTY_CELL(
        { b: Board, r: Int, c: Int, _: TileValue ->
            b.tileAt(r, c) == EmptyTile()
        }
    ),

    /**
     * A tile must not be set to an empty value.
     */
    CANNOT_SET_TILE_AS_EMPTY(
        { _: Board, _: Int, _: Int, v: TileValue -> v != TileValue.EMPTY }
    );

    fun check(board: Board, row: Int, col: Int, value: TileValue): Boolean = rule.invoke(board, row, col, value)
}

/**
 * Enum class with the possible winning conditions of a game.
 */
enum class WinningConditions(private val condition: (Board, Tile) -> Boolean) {
    ROW_MATCHES_VALUE({ b, v ->
        boardHasRowMatchingValue(
            b,
            v
        )
    }),
    COLUMN_MATCHES_VALUE({ b, v ->
        boardHasColumnMatchingValue(
            b,
            v
        )
    }),
    DIAGONAL_MATCHES_VALUE({ b, v ->
        boardHasDiagonalMatchingValue(
            b,
            v
        )
    });

    fun check(board: Board, value: Tile): Boolean = condition.invoke(board, value)
}

private fun boardHasRowMatchingValue(board: Board, value: Tile): Boolean {
    for (i in 0 until ROWS) {
        if (board.tileAt(i, 0) == value
            && board.tileAt(i, 1) == value
            && board.tileAt(i, 2) == value) {
            return true
        }
    }
    return false
}

private fun boardHasColumnMatchingValue(board: Board, value: Tile): Boolean {
    for (i in 0 until COLUMNS) {
        if (board.tileAt(0, i) == value
            && board.tileAt(1, i) == value
            && board.tileAt(2, i) == value) {
            return true
        }
    }
    return false
}

private fun boardHasDiagonalMatchingValue(board: Board, value: Tile): Boolean {
    return checkTopLeftToBottomRightDiagonal(
        board,
        value
    )
            || checkBottomLeftToTopRightDiagonal(
        board,
        value
    )
}

private fun checkTopLeftToBottomRightDiagonal(board: Board, value: Tile): Boolean {
    return board.tileAt(0, 0) == value
            && board.tileAt(1, 1) == value
            && board.tileAt(2, 2) == value
}

private fun checkBottomLeftToTopRightDiagonal(board: Board, value: Tile): Boolean {
    return board.tileAt(2, 0) == value
            && board.tileAt(1, 1) == value
            && board.tileAt(0, 2) == value
}