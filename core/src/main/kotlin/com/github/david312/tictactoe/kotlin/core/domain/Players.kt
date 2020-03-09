package com.github.david312.tictactoe.kotlin.core.domain

enum class PlayerTurn(val tile: Tile, val value: TileValue) {
    PLAYER_1(TicTile(), TileValue.TIC),
    PLAYER_2(TacTile(), TileValue.TAC)
}

