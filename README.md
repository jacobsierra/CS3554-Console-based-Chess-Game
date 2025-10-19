# CS3554-Console-based-Chess-Game

Console-based Chess Game: Project Instruction

**By:** Jacob Sierra & Robert Zamora

**Objective:** To design and implement a console-based chess game in Java that allows two players to play the classic game of chess against each other.

## Features

- ✅ Complete chess piece implementation (Pawn, Rook, Knight, Bishop, Queen, King)
- ✅ Full move validation for all pieces
- ✅ Check and checkmate detection
- ✅ Stalemate detection
- ✅ Special moves: castling, en passant, pawn promotion
- ✅ ASCII-based board display with coordinates
- ✅ Two-player gameplay with turn management
- ✅ Algebraic notation for move input (e.g., "E2 E4")

## How to Compile and Run

```bash
# Compile
javac game/Main.java

# Run
java game.Main
```

## Project Structure

The project follows proper package organization:

- **pieces/** - All chess piece classes (Piece, Pawn, Rook, Knight, Bishop, Queen, King)
- **board/** - Board class for game state management
- **players/** - Player class for user interaction
- **position/** - Position class for board coordinates
- **game/** - Game and Main classes for game orchestration
- **utils/** - Utilities class for helper functions
- **docs/** - Javadoc HTML documentation

## Documentation

Full API documentation is available in the `docs/` directory. Open `docs/index.html` in a web browser to view.

## Notes

We utilized Claude Code to help us write unit tests and the documentation so that we were certain about our input validation. If you'd like, we can provide the unit testing that was created for our game, as the documentation is also provided here in the repository.
