package com.example.xo1st

class Board {

    //Strings for PLAYER and COMPUTER
    companion object {
        const val PLAYER = "O"
        const val COMPUTER = "X"
    }

    //This is our internal board
    //and for this we used a 3 by 3 array of Strings
    val board = Array(3) { arrayOfNulls<String>(3) }

    //This property is giving us
    //a list of all the empty cells
    val availableCells: List<Cell>
        get() {
            val cells = mutableListOf<Cell>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j].isNullOrEmpty()) {
                        cells.add(Cell(i, j))
                    }
                }
            }
            return cells
        }

    //this property will tell
    //if the game is over or not
    val isGameOver: Boolean
        get() = hasComputerWon() || hasPlayerWon() || availableCells.isEmpty()


    //These functions are checking
    //Weather the computer or player has won or not
    fun hasComputerWon(): Boolean {
        //checks diagonals
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == COMPUTER ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == COMPUTER
        ) {
            return true
        }

        //checks lines and columns
        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == COMPUTER ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == COMPUTER
            ) {
                return true
            }
        }

        return false
    }

    fun hasPlayerWon(): Boolean {

        //checks diagonals
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == PLAYER ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == PLAYER
        ) {
            return true
        }

        //checks lines and columns
        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == PLAYER ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == PLAYER
            ) {
                return true
            }
        }

        return false
    }


    //in this var we will store the computersMove
    var computersMove: Cell? = null

    //this is our minimax function to calculate
    //the best move for the computer
    fun minimax(depth: Int, player: String): Int {
        //checks if game is over and who the winner is
        if (hasComputerWon()) return +1
        if (hasPlayerWon()) return -1

        if (availableCells.isEmpty()) return 0

        //initializing minimum and maximum values
        var min = Integer.MAX_VALUE
        var max = Integer.MIN_VALUE

        //checks all available cells
        //checks the two cases, when the computer makes a move and when the
        //player makes a move
        for (i in availableCells.indices) {
            val cell = availableCells[i]
            if (player == COMPUTER) {
                //place a move in this available cell
                placeMove(cell, COMPUTER)
                //recursively calculates the score for every possible set of moves
                val currentScore = minimax(depth + 1, PLAYER)
                max = Math.max(currentScore, max)

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = cell
                }

                //checks if computer has won
                if (currentScore == 1) {
                    board[cell.i][cell.j] = ""
                    break
                }

                //calculates the computer's move
                if (i == availableCells.size - 1 && max < 0) {
                    if (depth == 0) computersMove = cell
                }

            } else if (player == PLAYER) {
                placeMove(cell, PLAYER)
                //recursively calculates the score for every possible set of moves
                val currentScore = minimax(depth + 1, COMPUTER)
                min = Math.min(currentScore, min)

                //checks if player has won
                if (min == -1) {
                    board[cell.i][cell.j] = ""
                    break
                }
            }
            board[cell.i][cell.j] = ""
        }

        return if (player == COMPUTER) max else min
    }

    //this function is placing a move in the given cell
    fun placeMove(cell: Cell, player: String) {
        board[cell.i][cell.j] = player
    }

}