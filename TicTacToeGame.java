/*
    A game of tic-tac-toe in Java.

    Let's move quickly over difficult terrain.
*/

import java.io.*;
import java.util.*;
import java.lang.*;


class TicTacToeGame {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1", BoardState.PLAYER_1, 'X');
        Player player2 = new Player("Player 2", BoardState.PLAYER_2, 'O');

        boolean playerStarts = humanPlayerStarts();
        if (playerStarts) {
            System.out.println("You go first.");
        } else {
            System.out.println("Machine goes first.");
        }

        Board board = new Board(player1, player2);
        board.print();

        while(true) {
            player1.makeMove(board);
            board.print();
            if (board.isWon()) {
                board.winMessage();
                break;
            }

            player2.makeMove(board);
            board.print();
            if (board.isWon()) {
                board.winMessage();
                break;
            }
        }
    }

    private static boolean humanPlayerStarts() {
        Random rand = new Random(); 
        int value = rand.nextInt(2);

        if (value == 1) {
            return true;
        } else {
            return false;
        }
    }
}

/**
 * A player entity. May be the user or the computer.
 */
class Player {
    private String name;
    private BoardState state;
    private char token;
    private boolean isHuman;

    Player(String name, BoardState state, char token) {
        this.name = name;
        this.state = state;
        this.token = token;
        this.isHuman = true;
    }

    public String getName() {
        return name;
    }

    public BoardState getState() {
        return state;
    }

    public char getToken() {
        return token;
    }

    public void makeMove(Board board) {

        System.out.print(name + "'s move: ");

        if (isHuman) {
            Scanner scan = new Scanner(System.in);
            int position = scan.nextInt() - 1;
            board.update(this, position);
        }
    }
}

/**
 * The tic-tac-toe game board
 */
class Board {
    private Player player1;
    private Player player2;

    BoardSpace[] spaces = new BoardSpace[9];

    Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        for (int i=0; i < spaces.length; i++) {
            spaces[i] = new BoardSpace();
        }
    }

    public void update(Player player, int position) {
        spaces[position].setSpace(player.getState(), player.getToken());
    }

    public boolean isWon() {
        return true;
    }

    public void print() {

        // Example:
        //    |   |
        //  X | X | X
        // -------------
        //  X | X | X
        // -------------
        //  X | X | X 
        //    |   |

        String out = "\n"
        + "        |   |    \n"
        + "      " + spaces[0] + " | " + spaces[1] + " | " + spaces[2] + "  \n"
        + "    -------------\n" 
        + "      " + spaces[3] + " | " + spaces[4] + " | " + spaces[5] + "  \n"
        + "    -------------\n"
        + "      " + spaces[6] + " | " + spaces[7] + " | " + spaces[8] + "  \n"
        + "        |   |    \n"
        + "                 \n";

        System.out.print(out);
    }

    public void winMessage() {
        System.out.println("Nobody wins!");
    }
}

/**
 * One of the nine spaces on the tic-tac-toe board
 */
class BoardSpace {

    private BoardState state;
    private char token;

    BoardSpace() {
        token = ' ';
    }

    public void setSpace(BoardState state, char token) {
        this.state = state;
        this.token = token;
    }

    public String toString() {
        return Character.toString(token);
    }
}

/**
 * Status of a space on the board. Available, taken by Player 1, or taken by Player 2.
 */
enum BoardState {
    EMPTY, PLAYER_1, PLAYER_2
}