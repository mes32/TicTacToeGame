# TicTacToeGame

## Overview

Play a game of tic-tac-toe against the computer.

- Computer player's AI is based on the minimax algorithm
- Exception handling for illegal user inputs
- Randomizes order of turns (user first vs. computer first)
- Attempts to be easy to use within the constraints of a command line interface

## Setup

```
# Clone this repository
git clone https://github.com/mes32/TicTacToeGame.git

# Compile with Java 6 or later
cd TicTacToeGame/
javac TicTacToeGame.java

# Run with Java
java TicTacToeGame
```

## Usage

Player 1 is always X's and player 2 is always O's. The current state of the board prints on the left. Enter a number from 1 to 9 corresponding with your chosen space. The mapping between numbers and spaces prints to the right of the board for easy lookup.

```

        |   |    
        |   |        1   2   3  
    -------------
        |   |        4   5   6  
    -------------
        |   |        7   8   9  
        |   |    

Player 1's move: 
```

## References

I found this article on minimax and tic-tac-toe useful when creating this program [neverstopbuilding.com/minimax](http://neverstopbuilding.com/minimax).

## License

The code in this repository is licensed under the [MIT License](./LICENSE).