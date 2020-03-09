package com.github.david312.tictactoe.kotlin.core.exceptions

import com.github.david312.tictactoe.kotlin.core.domain.TileValue

class InvalidBoardLocationException(row: Int, column: Int) :
    RuntimeException("Invalid board location at $row, $column")

class IllegalPlayerMoveException(row: Int, column: Int, tileValue: TileValue) :
    RuntimeException("Illegal move detected at tile [$row, $column]. Tile already has a not empty value: $tileValue")