package com.github.david312.tictactoe.kotlin.core

import com.github.david312.tictactoe.kotlin.core.domain.Board
import com.github.david312.tictactoe.kotlin.core.domain.PlayerTurn
import com.github.david312.tictactoe.kotlin.core.exceptions.IllegalPlayerMoveException
import com.github.david312.tictactoe.kotlin.core.exceptions.InvalidBoardLocationException
import com.github.david312.tictactoe.kotlin.core.logic.BoardRules

class Game(
    private val board: Board = Board(),
    private var playerTurn: PlayerTurn = PlayerTurn.PLAYER_1,
    private val scores: MutableMap<PlayerTurn, Int> = mutableMapOf(
        PlayerTurn.PLAYER_1 to 0,
        PlayerTurn.PLAYER_2 to 0
    )
) {
    val currentPlayer: PlayerTurn
        get() = playerTurn
    val scoreboard: Map<PlayerTurn, Int>
        get() = scores.toMap()

    /**
     * Fills the tile at the given location with the value
     * of the current player's mark.
     *
     * The next player turn will be updated automatically except
     * when an invalid action is detected.
     *
     * @param row the row where the desired tile to mark is located.
     * @param column the column where the desired tile to mark is located.
     * @throws InvalidBoardLocationException when the location is not inside the board.
     * @throws IllegalPlayerMoveException when the tile to mark is not empty.
     */
    fun mark(row: Int, column: Int) {
        try {
            validateMove(row, column)
            board.mark(row, column, playerTurn.tileValue)
            nextTurn()
        } catch (e: IndexOutOfBoundsException) {
            throw InvalidBoardLocationException(row, column)
        }
    }

    private fun validateMove(row: Int, column: Int) {
        if (!BoardRules.CANNOT_OVERWRITE_NON_EMPTY_CELL.check(board, row, column, playerTurn.tileValue)) {
            throw IllegalPlayerMoveException(row, column, board.tileAt(row, column).value)
        }
    }

    private fun nextTurn() {
        playerTurn = when(playerTurn) {
            PlayerTurn.PLAYER_1 -> PlayerTurn.PLAYER_2
            PlayerTurn.PLAYER_2 -> PlayerTurn.PLAYER_1
        }
    }
}