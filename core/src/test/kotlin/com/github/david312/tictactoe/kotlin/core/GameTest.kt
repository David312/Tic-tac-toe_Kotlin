package com.github.david312.tictactoe.kotlin.core

import com.github.david312.tictactoe.kotlin.core.domain.Board
import com.github.david312.tictactoe.kotlin.core.domain.PlayerTurn
import com.github.david312.tictactoe.kotlin.core.domain.ROWS
import com.github.david312.tictactoe.kotlin.core.exceptions.IllegalPlayerMoveException
import com.github.david312.tictactoe.kotlin.core.exceptions.InvalidBoardLocationException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import kotlin.test.assertEquals

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
}