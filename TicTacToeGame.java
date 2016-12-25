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

        if (computerGoesFirst()) {
            System.out.println("Machine goes first.");
            player1 = new ComputerPlayer(player1, player2);
        } else {
            System.out.println("You go first.");
            player2 = new ComputerPlayer(player2, player1);
        }

        Board board = new Board(player1, player2);
        board.print();

        while(true) {
            player1.makeMove(board);
            board.print();
            if (board.isFinished(player1)) {
                board.printWinMessage();
                break;
            }

            player2.makeMove(board);
            board.print();
            if (board.isFinished(player2)) {
                board.printWinMessage();
                break;
            }
        }
    }

    private static boolean computerGoesFirst() {
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
 * A general player entity
 */
class Player {
    protected String name;
    protected BoardState state;
    protected char token;

    Player(String name, BoardState state, char token) {
        this.name = name;
        this.state = state;
        this.token = token;
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

        try {
            Scanner scan = new Scanner(System.in);
            int position = scan.nextInt() - 1;
            board.update(this, position);
        } catch (InputMismatchException e) {
            System.err.println("  ***  Must be an integer. Try again.  ***");
            makeMove(board);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("  ***  Must be in range 1 through 9. Try again.  ***");
            makeMove(board);
        } catch (SpaceTakenException e) {
            System.err.println("  ***  That space is already taken. Try again.  ***");
            makeMove(board);    
        }
    }
}

/**
 * A computer player
 */
class ComputerPlayer extends Player {

    private Player otherPlayer;

    ComputerPlayer(Player self, Player other) {
        super(self.getName(), self.getState(), self.getToken());
        otherPlayer = other;
    }

    @Override
    public void makeMove(Board board) {

        int position = aiMove(board);
        System.out.println(name + "'s move: " + (position + 1));

        try {
            board.update(this, position);
        } catch (InputMismatchException e) {
            System.err.println("  ***  Must be an integer. Try again.  ***");
            makeMove(board);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("  ***  Must be in range 1 through 9. Try again.  ***");
            makeMove(board);
        } catch (SpaceTakenException e) {
            System.err.println("  ***  That space is already taken. Try again.  ***");
            makeMove(board);    
        }
    }

    private int aiMove(Board board) {
        int[] validMoves = board.listEmptySpaces();
        Random rand = new Random();
        int index = rand.nextInt(validMoves.length);
        return validMoves[index];
    }

    private int minimax(Board board, int move, Player currentPlayer, int depth) {
        return 0;
    }

    private int scoreOutcome(Board hypothetical, int depth) {
        if (hypothetical.hasPlayerWon(this)) {
            return 10 - depth;
        } else if (hypothetical.isFilled()) {
            return 0;
        } else {
            return -10 + depth;
        }
    }
}

/**
 * The tic-tac-toe game board
 */
class Board {
    private Player player1;
    private Player player2;

    private String winMessage = "";

    BoardSpace[] spaces = new BoardSpace[9];

    Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        for (int i=0; i < spaces.length; i++) {
            spaces[i] = new BoardSpace();
        }
    }

    public void update(Player player, int position) throws SpaceTakenException {
        if (spaces[position].getState() != BoardState.EMPTY) {
            throw new SpaceTakenException();
        }
        spaces[position].set(player.getState(), player.getToken());
    }

    public boolean isFinished(Player currentPlayer) {

        if (hasPlayerWon(currentPlayer)) {
            winMessage = currentPlayer.getName() + " wins!";
            return true;
        }

        if (isFilled()) {
            winMessage = "Nobody wins!";
            return true;
        }

        return false;
    }

    public boolean isFilled() {

        for (int i=0; i < spaces.length; i++) {
            if (spaces[i].getState() == BoardState.EMPTY) {
                return false;
            }
        }

        return true;
    }

    public boolean hasPlayerWon(Player player) {

        // Board as indexed by the array 'spaces':
        //  0  1  2
        //  3  4  5
        //  6  7  8 

        BoardState p = player.getState();
        if (spaces[4].getState() == p) {
            // Check for lines passing though center
            if (spaces[1].getState() == p && spaces[7].getState() == p ||
                spaces[3].getState() == p && spaces[5].getState() == p ||
                spaces[0].getState() == p && spaces[8].getState() == p ||
                spaces[2].getState() == p && spaces[6].getState() == p ) {
                return true;
            }
        }

        if (spaces[0].getState() == p) {
            // Check for edge lines passing though top left
            if (spaces[1].getState() == p && spaces[2].getState() == p ||
                spaces[3].getState() == p && spaces[6].getState() == p ) {
                return true;
            }
        }
        
        if (spaces[8].getState() == p) {
            // Check for edge lines passing though bottom right
            if (spaces[2].getState() == p && spaces[5].getState() == p ||
                spaces[6].getState() == p && spaces[7].getState() == p ) {
                return true;
            }
        }

        return false;
    }

    public void print() {

        // Example of board printed to stdout:
        //    |   |
        //  X | X | X       1   2   3
        // -------------
        //  X | X | X       4   5   6
        // -------------
        //  X | X | X       7   8   9 
        //    |   |

        String out = "\n"
        + "        |   |    \n"
        + "      " + spaces[0] + " | " + spaces[1] + " | " + spaces[2] + "      1   2   3  \n"
        + "    -------------\n" 
        + "      " + spaces[3] + " | " + spaces[4] + " | " + spaces[5] + "      4   5   6  \n"
        + "    -------------\n"
        + "      " + spaces[6] + " | " + spaces[7] + " | " + spaces[8] + "      7   8   9  \n"
        + "        |   |    \n"
        + "                 \n";

        System.out.print(out);
    }

    public int[] listEmptySpaces() {

        List<Integer> list = new ArrayList<Integer>();

        for (int i=0; i < spaces.length; i++) {
            if (spaces[i].getState() == BoardState.EMPTY) {
                list.add(i);
            }
        }

        int[] array = new int[list.size()];
        for (int i=0; i < array.length; i++) {
            array[i] = list.get(i).intValue();
        }
        return array;
    }

    public void printWinMessage() {
        System.out.println(winMessage);
    }
}

/**
 * One of the nine spaces on the tic-tac-toe board
 */
class BoardSpace {

    private BoardState state;
    private char token;

    BoardSpace() {
        state = BoardState.EMPTY;
        token = ' ';
    }

    public void set(BoardState state, char token) {
        this.state = state;
        this.token = token;
    }

    public BoardState getState() {
        return state;
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

class SpaceTakenException extends Exception {
    SpaceTakenException() {
        super();
    }
}