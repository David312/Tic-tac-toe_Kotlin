package com.github.david312.tictactoe.kotlin.core

import com.github.david312.tictactoe.kotlin.core.domain.PlayerTurn
import com.github.david312.tictactoe.kotlin.core.domain.ROWS
import com.github.david312.tictactoe.kotlin.core.exceptions.GameAlreadyFinishedException
import com.github.david312.tictactoe.kotlin.core.exceptions.IllegalPlayerMoveException
import com.github.david312.tictactoe.kotlin.core.exceptions.InvalidBoardLocationException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GameTest {
    private lateinit var game: Game

    @BeforeEach
    fun setUp() {
        game = Game()
    }

    @Test
    fun `A game should start with an empty scoreboard and the player 1 as the first player`() {
        val scoreboard = game.scoreboard
        assertEquals(scoreboard, mapOf(
            PlayerTurn.PLAYER_1 to 0,
            PlayerTurn.PLAYER_2 to 0
        ))
        assertEquals(PlayerTurn.PLAYER_1, game.currentPlayer)
    }

    @Test
    fun `When you try to mark an out of bounds tile the game should throw InvalidBoardLocationException`() {
        assertThrows<InvalidBoardLocationException> { game.mark(-1, 0) }
        assertThrows<InvalidBoardLocationException> { game.mark(0, ROWS) }
    }

    @Test
    fun `When a tile is marked then the turn should change into the next player`() {
        game.mark(0, 0)
        assertEquals(PlayerTurn.PLAYER_2, game.currentPlayer)
        game.mark(0, 1)
        assertEquals(PlayerTurn.PLAYER_1, game.currentPlayer)
    }

    @Test
    fun `When you try to set a not empty tile the game should throw IllegalPlayerMoveException`() {
        game.mark(0, 0)
        assertThrows<IllegalPlayerMoveException> { game.mark(0, 0) }
    }

    @Test
    fun `When you try to set a not empty tile the game should not change player's turn`() {
        game.mark(0, 0)
        assertThrows<IllegalPlayerMoveException> { game.mark(0, 0) }
        assertEquals(PlayerTurn.PLAYER_2, game.currentPlayer)
    }

    @Test
    fun `When a player wins then no more move should be allowed`() {
        makeWinningGame()
        assertThrows<GameAlreadyFinishedException> { game.mark(1, 2) }
    }

    private fun makeWinningGame() {
        // [X][X][X]
        // [O][O][ ]
        // [ ][ ][ ]
        game.mark(0, 0) // P1
        game.mark(1, 0) // P2
        game.mark(0, 1) // P1
        game.mark(1, 1) // P2
        game.mark(0, 2) // P1 Wins
    }

    @Test
    fun `When nobody wins then no more moves should be allowed`() {
        makeDrawGame()
        assertThrows<GameAlreadyFinishedException> { game.mark(1, 1) }
    }

    private fun makeDrawGame() {
        // [X][O][X]
        // [X][O][O]
        // [O][X][X]
        game.mark(0, 0) // P1
        game.mark(0, 1) // P2
        game.mark(0, 2) // P1
        game.mark(1, 1) // P2
        game.mark(1, 0) // P1
        game.mark(1, 2) // P2
        game.mark(2, 1) // P1
        game.mark(2, 0) // P2
        game.mark(2, 2) // P1
    }

    @Test
    fun `When a player wins then the next turn should not be updated`() {
        makeWinningGame()
        assertEquals(PlayerTurn.PLAYER_1, game.currentPlayer)
    }

    @Test
    fun `When nobody wins then the next turn should not be updated`() {
        makeDrawGame()
        assertEquals(PlayerTurn.PLAYER_1, game.currentPlayer)
    }

    @Test
    fun `A game is finished when a winner or a draw is detected`() {
        assertFalse(game.isFinished)

        makeWinningGame()
        assertTrue(game.isFinished)

        game = Game()
        makeDrawGame()
        assertTrue(game.isFinished)
    }

    @Test
    fun `A winner should be returned when there is no draw`() {
        makeWinningGame()
        assertEquals(PlayerTurn.PLAYER_1, game.winner)
    }

    @Test
    fun `No winner should be returned when there is a draw`() {
        makeDrawGame()
        assertNull(game.winner)
    }

    @Test
    fun `When a player wins its score should increase`() {
        assertEquals(mapOf(
            PlayerTurn.PLAYER_1 to 0,
            PlayerTurn.PLAYER_2 to 0
        ), game.scoreboard)

        makeWinningGame()

        assertEquals(mapOf(
            PlayerTurn.PLAYER_1 to 1,
            PlayerTurn.PLAYER_2 to 0
        ), game.scoreboard)
    }

    @Test
    fun `When nobody wins the score should be remain intact`() {
        assertEquals(mapOf(
            PlayerTurn.PLAYER_1 to 0,
            PlayerTurn.PLAYER_2 to 0
        ), game.scoreboard)

        makeDrawGame()

        assertEquals(mapOf(
            PlayerTurn.PLAYER_1 to 0,
            PlayerTurn.PLAYER_2 to 0
        ), game.scoreboard)
    }
}