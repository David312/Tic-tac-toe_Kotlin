package com.github.david312.tictactoe.kotlin.core.domain

/**
 * Possible values that a tile can have.
 * @param symbol the string representing its value.
 */
enum class TileValue(val symbol: String) { TIC("X"), TAC("O"), EMPTY(" ") }

/**
 * Class representing a single tile.
 */
sealed class Tile(private val value: TileValue) {
    override fun toString(): String = value.symbol
    override fun equals(other: Any?): Boolean {
        return when(other) {
            is Tile -> other.value == value
            else -> false
        }
    }
    // TODO: override hashCode
}

/**
 * A Tile marked with a TIC value.
 */
class TicTile : Tile(TileValue.TIC)

/**
 * A Tile marked with a TAC value.
 */
class TacTile : Tile(TileValue.TAC)

/**
 * A Tile marked with no value.
 */
class EmptyTile : Tile(TileValue.EMPTY)
