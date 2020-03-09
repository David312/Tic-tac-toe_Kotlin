package com.github.david312.tictactoe.kotlin.core.domain

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Test
    fun `A board should be initialized with empty values`() {
        checkBoardIsEmpty(board)
    }

    private fun checkBoardIsEmpty(board: Board) {
        for (i in 0 until ROWS) {
            for (j in 0 until COLUMNS) {
                assertEquals(EmptyTile(), board.tileAt(i, j))
            }
        }
    }

    @Test
    fun `Marking a Board tile with a not empty value should be reflected`() {
        board.mark(1, 1, TileValue.TIC)
        assertEquals(TicTile(), board.tileAt(1, 1))
    }

    @Test
    fun `If a Board is cleaned it should contain only empty tiles`() {
        board.mark(1, 1, TileValue.TIC)
        board.clear()
        checkBoardIsEmpty(board)
    }

    @Test
    fun `A Board should be printed as a 3x3 grid`() {
        val expected: String =
            """
            [ ][ ][ ]
            [ ][ ][ ]
            [ ][ ][ ]
            """.trimIndent()
        assertEquals(expected, board.toString())
    }

    @Test
    fun `A board is equals to another board if all tiles match`() {
        val other = Board()
        assertEquals(board, other)
        assertEquals(board, board)
        assertNotEquals(board, null as Board?)

        other.mark(1, 1, TileValue.TIC)
        assertNotEquals(board, other)
    }
}