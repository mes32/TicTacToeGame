/*
    A game of tic-tac-toe in Java.

    Let's move quickly over difficult terrain.
*/

import java.io.*;
import java.util.*;

class TicTacToeGame {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1", 'X');
        Player player2 = new Player("Player 2", 'O');

        boolean playerStarts = humanPlayerStarts();
        if (playerStarts) {
            System.out.println("Player goes first.");
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

            break;
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

class Player {
    private String name;
    private char token;
    private boolean isHuman;

    Player(String name, char token) {
        this.name = name;
        this.token = token;
        this.isHuman = true;
    }

    public String getName() {
        return name;
    }

    public char getToken() {
        return token;
    }

    public void makeMove(Board board) {
        board.update(token, 0);
    }
}

class Board {
    private Player player1;
    private Player player2;

    Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void update(char token, int move) {

    }

    public boolean isWon() {
        return true;
    }

    public void print() {
        System.out.println("[Board goes here]");
    }

    public void winMessage() {
        System.out.println("Nobody wins!");
    }
}