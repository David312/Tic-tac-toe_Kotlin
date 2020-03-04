package com.github.david312.tictactoe.kotlin.core.domain

val ROWS = 3
val COLUMNS = 3

/**
 * Representation of a Tic-tac-toe board game.
 * It holds the current state of the tiles of the game and provides
 * an API to manipulate its state.
 */
class Board {
    private var rows: List<MutableList<Tile>> = listOf(
        mutableListOf<Tile>(EmptyTile(), EmptyTile(), EmptyTile()),
        mutableListOf<Tile>(EmptyTile(), EmptyTile(), EmptyTile()),
        mutableListOf<Tile>(EmptyTile(), EmptyTile(), EmptyTile())
    )

    /**
     * Gets the tile located at the given (row, column) location.
     *
     * @param row the row index (zero-based).
     * @param column the column index (zero-based).
     * @return the tile correspondent to (row, column) location.
     */
    fun tileAt(row: Int, column: Int): Tile {
        return rows[row][column]
    }

    /**
     * Marks a tile with the value provided at the given location (row, column).
     *
     * @param row the row index (zero-based).
     * @param column the column index (zero-based).
     * @param value the value to mark the tile with.
     */
    fun mark(row: Int, column: Int, value: TileValue): Unit {
        rows[row][column] = when(value) {
            TileValue.TIC -> TicTile()
            TileValue.TAC -> TacTile()
            TileValue.EMPTY -> EmptyTile()
        }
    }

    /**
     * Cleans the board by marking all tiles with empty tiles.
     */
    fun clear(): Unit {
        rows = listOf(
            mutableListOf<Tile>(EmptyTile(), EmptyTile(), EmptyTile()),
            mutableListOf<Tile>(EmptyTile(), EmptyTile(), EmptyTile()),
            mutableListOf<Tile>(EmptyTile(), EmptyTile(), EmptyTile())
        )
    }

    override fun toString(): String {
        return rows.map {
                row -> row.map { "[$it]" }.reduce { acc, s -> acc + s }
        }.reduce { acc, s -> acc + s + "\n" }
    }
}