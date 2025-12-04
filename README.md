# CS3554 Chess Game

**By:** Jacob Sierra & Robert Zamora

A fully-featured chess game implemented in Java with both a graphical user interface (GUI) and console-based version.

## Features

### Core Chess Rules
- ✅ Complete chess piece implementation (Pawn, Rook, Knight, Bishop, Queen, King)
- ✅ Full move validation for all pieces
- ✅ Check and checkmate detection
- ✅ Stalemate detection
- ✅ Special moves: castling, en passant, pawn promotion
- ✅ Prevents illegal moves (moving into check, castling through check, etc.)

### GUI Features (Phase 2)
- ✅ 8x8 graphical chessboard with alternating colors
- ✅ Unicode chess piece symbols
- ✅ Click-to-move functionality
- ✅ Drag-and-drop piece movement
- ✅ Coordinate labels (A-H, 1-8) on board edges
- ✅ Visual square highlighting for selected pieces
- ✅ Menu bar with File operations
- ✅ Save/Load game functionality
- ✅ New game option
- ✅ Check notification in window title
- ✅ Game over detection with popup messages

## How to Run

### GUI Version (Recommended)

```bash
# Compile
javac gui/ChessGUI.java

# Run
java gui.ChessGUI
```

Or simply run `gui/ChessGUI.java` from your IDE.

### Console Version

```bash
# Compile
javac game/Main.java

# Run
java game.Main
```

## How to Play

### GUI Controls
- **Click-to-move**: Click on a piece to select it, then click on the destination square
- **Drag-and-drop**: Click and hold a piece, drag it to the destination, and release

### Menu Options
- **New Game** (Ctrl+N): Start a new game
- **Save Game** (Ctrl+S): Save the current game state to a file
- **Load Game** (Ctrl+L): Load a previously saved game
- **Exit**: Close the application

### Special Moves
- **Castling**: Move the King two squares toward a Rook (only when neither has moved, squares between are empty, and King is not in/moving through check)
- **En Passant**: Pawn captures opponent's pawn that just moved two squares forward
- **Pawn Promotion**: When a pawn reaches the opposite end, choose Queen, Rook, Bishop, or Knight

## Project Structure

```
├── gui/              - Graphical user interface
│   ├── ChessGUI.java - Main GUI implementation
│   └── GameState.java - Serializable game state for save/load
├── board/            - Board class for game state management
├── pieces/           - All chess piece classes
│   ├── Piece.java    - Abstract base class
│   ├── Pawn.java
│   ├── Rook.java
│   ├── Knight.java
│   ├── Bishop.java
│   ├── Queen.java
│   └── King.java
├── position/         - Position class for board coordinates
├── game/             - Console version game logic
├── utils/            - Utility classes
└── docs/             - Javadoc documentation
```

## Requirements

- Java 8 or higher
- No external dependencies required

## Recent Bug Fixes

All critical chess rule violations have been fixed:
- En passant now works correctly across multiple moves
- Move simulation properly updates piece positions for accurate check detection
- Castling validation prevents illegal castling while in check or through check
- Checkmate detection properly implemented (King capture logic removed)

## Documentation

Full API documentation is available in the `docs/` directory. Open `docs/index.html` in a web browser to view the complete Javadoc.

## Development

This project was developed for CS3554 using proper software engineering practices including:
- Object-oriented design with inheritance and polymorphism
- Comprehensive move validation and game state management
- Event-driven GUI programming with Java Swing
- Serialization for save/load functionality
- Extensive testing and debugging
