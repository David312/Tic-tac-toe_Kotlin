package com.github.david312.tictactoe.kotlin.core.domain

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TilesTest {

    lateinit var tile: Tile

    @Test
    fun `An Empty tile should be represented as " "`() {
        tile = EmptyTile()
        assertEquals(" ", tile.toString())
    }

    @Test
    fun `A Tic tile should be represented as "X"`() {
        tile = TicTile()
        assertEquals("X", tile.toString())
    }

    @Test
    fun `A Tac tile should be representeed as "O"`() {
        tile = TacTile()
        assertEquals("O", tile.toString())
    }
}