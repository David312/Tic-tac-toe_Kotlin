package com.github.david312.tictactoe.kotlin.core.logic

import com.github.david312.tictactoe.kotlin.core.domain.Board
import com.github.david312.tictactoe.kotlin.core.domain.TicTile
import com.github.david312.tictactoe.kotlin.core.domain.TileValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RulesTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board()
    }

    @Nested
    inner class BoardRulesTests {

        @Test
        fun `A not empty cell should not be overwritten`() {
            board.mark(1, 1, TileValue.TIC)
            assertFalse(BoardRules.CANNOT_OVERWRITE_NON_EMPTY_CELL.check(board, 1, 1,
                TileValue.TAC
            ))
        }

        @Test
        fun `An empty cell can be overwritten`() {
            assertTrue(BoardRules.CANNOT_OVERWRITE_NON_EMPTY_CELL.check(board, 1, 1,
                TileValue.TAC
            ))
        }

        @Test
        fun `A cell should not be set to empty value`() {
            assertFalse(BoardRules.CANNOT_SET_TILE_AS_EMPTY.check(board, 1, 1,
                TileValue.EMPTY
            ))
            assertTrue(BoardRules.CANNOT_SET_TILE_AS_EMPTY.check(board, 1, 1,
                TileValue.TIC
            ))
        }
    }

    @Nested
    inner class WinningConditionsTests {

        @Test
        fun `A board with a row that matches the given value should return true`() {
            board.clear()
            assertFalse(WinningConditions.ROW_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(0, 0, TileValue.TIC)
            board.mark(0, 1, TileValue.TIC)
            board.mark(0, 2, TileValue.TIC)
            assertTrue(WinningConditions.ROW_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(0, 1, TileValue.TAC)
            assertFalse(WinningConditions.ROW_MATCHES_VALUE.check(board,
                TicTile()
            ))
        }

        @Test
        fun `A board with a column that matches the given value should return true`() {
            board.clear()
            assertFalse(WinningConditions.COLUMN_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(0, 0, TileValue.TIC)
            board.mark(1, 0, TileValue.TIC)
            board.mark(2, 0, TileValue.TIC)
            assertTrue(WinningConditions.COLUMN_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(1, 0, TileValue.TAC)
            assertFalse(WinningConditions.COLUMN_MATCHES_VALUE.check(board,
                TicTile()
            ))
        }

        @Test
        fun `A board with the top-left to bottom-right diagonal that matches the given value should return true`() {
            board.clear()
            assertFalse(WinningConditions.DIAGONAL_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(0, 0, TileValue.TIC)
            board.mark(1, 1, TileValue.TIC)
            board.mark(2, 2, TileValue.TIC)
            assertTrue(WinningConditions.DIAGONAL_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(1, 1, TileValue.TAC)
            assertFalse(WinningConditions.DIAGONAL_MATCHES_VALUE.check(board,
                TicTile()
            ))
        }

        @Test
        fun `A board with the bottom-left to top-right diagonal that matches the given value should return true`() {
            board.clear()
            assertFalse(WinningConditions.DIAGONAL_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(2, 0, TileValue.TIC)
            board.mark(1, 1, TileValue.TIC)
            board.mark(0, 2, TileValue.TIC)
            assertTrue(WinningConditions.DIAGONAL_MATCHES_VALUE.check(board,
                TicTile()
            ))

            board.mark(1, 1, TileValue.TAC)
            assertFalse(WinningConditions.DIAGONAL_MATCHES_VALUE.check(board,
                TicTile()
            ))
        }
    }
}