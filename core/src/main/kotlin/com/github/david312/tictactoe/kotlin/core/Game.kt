package com.github.david312.tictactoe.kotlin.core

import com.github.david312.tictactoe.kotlin.core.domain.Board
import com.github.david312.tictactoe.kotlin.core.domain.PlayerTurn
import com.github.david312.tictactoe.kotlin.core.exceptions.GameAlreadyFinishedException
import com.github.david312.tictactoe.kotlin.core.exceptions.IllegalPlayerMoveException
import com.github.david312.tictactoe.kotlin.core.exceptions.InvalidBoardLocationException
import com.github.david312.tictactoe.kotlin.core.logic.BoardRules
import com.github.david312.tictactoe.kotlin.core.logic.WinningConditions

class Game(
    private val board: Board = Board(),
    private var playerTurn: PlayerTurn = PlayerTurn.PLAYER_1,
    private val scores: MutableMap<PlayerTurn, Int> = mutableMapOf(
        PlayerTurn.PLAYER_1 to 0,
        PlayerTurn.PLAYER_2 to 0
    ),
    private var gameFinished: Boolean = false
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
     * @throws GameAlreadyFinishedException when the game is over and you try to mark a tile.
     */
    fun mark(row: Int, column: Int) {
        try {
            ensureGameIsNotFinished()
            validateMove(row, column)
            board.mark(row, column, playerTurn.value)
            updateGameStatus()
        } catch (e: IndexOutOfBoundsException) {
            throw InvalidBoardLocationException(row, column)
        }
    }

    private fun ensureGameIsNotFinished() {
        if (gameFinished) {
            throw GameAlreadyFinishedException()
        }
    }

    private fun validateMove(row: Int, column: Int) {
        if (!BoardRules.CANNOT_OVERWRITE_NON_EMPTY_CELL.check(board, row, column, playerTurn.value)) {
            throw IllegalPlayerMoveException(row, column, board.tileAt(row, column).value)
        }
    }

    private fun updateGameStatus() {
        if (winnerDetected() || noMoreMovesAllowed()) {
            gameFinished = true
        } else {
            nextTurn()
        }
    }

    private fun winnerDetected(): Boolean {
        return WinningConditions.values().any { it.check(board, playerTurn.tile) }
    }

    private fun noMoreMovesAllowed(): Boolean {
        return board.isFull()
    }

    private fun nextTurn() {
        playerTurn = when(playerTurn) {
            PlayerTurn.PLAYER_1 -> PlayerTurn.PLAYER_2
            PlayerTurn.PLAYER_2 -> PlayerTurn.PLAYER_1
        }
    }
}