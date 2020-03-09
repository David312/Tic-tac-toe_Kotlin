package com.github.david312.tictactoe.kotlin.core

import com.github.david312.tictactoe.kotlin.core.domain.Board
import com.github.david312.tictactoe.kotlin.core.domain.PlayerTurn
import com.github.david312.tictactoe.kotlin.core.exceptions.GameAlreadyFinishedException
import com.github.david312.tictactoe.kotlin.core.exceptions.IllegalPlayerMoveException
import com.github.david312.tictactoe.kotlin.core.exceptions.InvalidBoardLocationException
import com.github.david312.tictactoe.kotlin.core.logic.BoardRules
import com.github.david312.tictactoe.kotlin.core.logic.WinningConditions

class Game(
    private var board: Board = Board(),
    private var playerTurn: PlayerTurn = PlayerTurn.PLAYER_1,
    private val scores: MutableMap<PlayerTurn, Int> = mutableMapOf(
        PlayerTurn.PLAYER_1 to 0,
        PlayerTurn.PLAYER_2 to 0
    ),
    private var gameFinished: Boolean = false,
    private var winnerPlayer: PlayerTurn? = null
) {
    val currentPlayer: PlayerTurn
        get() = playerTurn
    val scoreboard: Map<PlayerTurn, Int>
        get() = scores.toMap()
    val isFinished: Boolean
        get() = gameFinished
    val winner: PlayerTurn?
        get() = winnerPlayer

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
        val winnerDetected = winnerDetected()
        gameFinished = winnerDetected || noMoreMovesAllowed()
        if (winnerDetected) {
            winnerPlayer = playerTurn
        }
        if (!gameFinished) {
            nextTurn()
        } else {
            updateScores()
        }
    }

    private fun winnerDetected(): Boolean {
        return WinningConditions.values().any { it.check(board, playerTurn.tile) }
    }

    private fun noMoreMovesAllowed(): Boolean {
        return board.isFull()
    }

    private fun updateScores() {
        if (winnerPlayer != null) {
            val player: PlayerTurn = winnerPlayer!!
            // player should always exist in the map.
            scores[player] = scores[player]!! + 1
        }
    }

    private fun nextTurn() {
        playerTurn = when(playerTurn) {
            PlayerTurn.PLAYER_1 -> PlayerTurn.PLAYER_2
            PlayerTurn.PLAYER_2 -> PlayerTurn.PLAYER_1
        }
    }

    /**
     * Starts a new game.
     * The first player will be the one who should be next on the previous game.
     */
    fun newGame() {
        // update next turn.
        nextTurn()
        board = Board()
        gameFinished = false
        winnerPlayer = null
    }
}