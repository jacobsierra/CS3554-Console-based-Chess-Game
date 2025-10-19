package board;

import pieces.*;
import position.Position;
import java.util.List;

/**
 * Represents an 8x8 chess board with pieces. Manages board state, piece movement,
 * special moves (castling, en passant), and check/checkmate detection.
 * The board uses array indices where row 0 = rank 8 (black's back rank)
 * and row 7 = rank 1 (white's back rank).
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Board {
    private Piece[][] grid = new Piece[8][8];
    private Position enPassantTarget = null;

    /**
     * Constructs a new chess board and initializes it with pieces
     * in their standard starting positions.
     */
    public Board() {
        initialize();
    }

    /**
     * Initializes the board with pieces in standard chess starting positions.
     * Sets up white pieces on ranks 1-2 (rows 6-7) and black pieces on ranks 7-8 (rows 0-1).
     */
    public void initialize() {
        for (int col = 0; col < 8; col++) {
            grid[6][col] = new Pawn("white", new Position(6, col));
        }
        
        for (int col = 0; col < 8; col++) {
            grid[1][col] = new Pawn("black", new Position(1, col));
        }

        grid[7][0] = new Rook("white", new Position(7, 0));
        grid[7][1] = new Knight("white", new Position(7, 1));
        grid[7][2] = new Bishop("white", new Position(7, 2));
        grid[7][3] = new Queen("white", new Position(7, 3));
        grid[7][4] = new King("white", new Position(7, 4));
        grid[7][5] = new Bishop("white", new Position(7, 5));
        grid[7][6] = new Knight("white", new Position(7, 6));
        grid[7][7] = new Rook("white", new Position(7, 7));

        grid[0][0] = new Rook("black", new Position(0, 0));
        grid[0][1] = new Knight("black", new Position(0, 1));
        grid[0][2] = new Bishop("black", new Position(0, 2));
        grid[0][3] = new Queen("black", new Position(0, 3));
        grid[0][4] = new King("black", new Position(0, 4));
        grid[0][5] = new Bishop("black", new Position(0, 5));
        grid[0][6] = new Knight("black", new Position(0, 6));
        grid[0][7] = new Rook("black", new Position(0, 7));
    }

    /**
     * Retrieves the piece at the specified position.
     *
     * @param position the board position to check
     * @return the Piece at the position, or null if the square is empty
     */
    public Piece getPiece(Position position) {
        return grid[position.row][position.col];
    }

    /**
     * Moves a piece from one position to another. Handles special moves including:
     * - En passant capture
     * - Castling (moving both king and rook)
     * - Two-square pawn moves (sets en passant target)
     * Updates the piece's internal position and the hasMoved flag.
     *
     * @param from the starting position
     * @param to the destination position
     */
    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece != null) {
            // Handle en passant capture
            if (piece instanceof Pawn && to.col != from.col && grid[to.row][to.col] == null) {
                // En passant capture - remove the captured pawn
                grid[from.row][to.col] = null;
            }

            // Handle castling - move the rook
            if (piece instanceof King && Math.abs(to.col - from.col) == 2) {
                int row = from.row;
                if (to.col == 6) {
                    // Kingside castling
                    Piece rook = grid[row][7];
                    grid[row][5] = rook;
                    grid[row][7] = null;
                    rook.move(new Position(row, 5));
                } else if (to.col == 2) {
                    // Queenside castling
                    Piece rook = grid[row][0];
                    grid[row][3] = rook;
                    grid[row][0] = null;
                    rook.move(new Position(row, 3));
                }
            }

            // Check if this is a two-square pawn move (for en passant tracking)
            enPassantTarget = null;
            if (piece instanceof Pawn && Math.abs(to.row - from.row) == 2) {
                int direction = piece.getColor().equals("white") ? -1 : 1;
                enPassantTarget = new Position(from.row + direction, from.col);
            }

            grid[to.row][to.col] = piece;
            grid[from.row][from.col] = null;
            piece.move(to);
        }
    }

    /**
     * Displays the current board state to the console using ASCII representation.
     * Shows pieces with notation (e.g., "wp" for white pawn, "bK" for black king).
     * Empty squares are shown as "##" (dark squares) or "   " (light squares).
     */
    public void display() {
        System.out.println();
        for (int row = 0; row < 8; row++) {
            System.out.print(8 - row + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece == null) {
                    System.out.print((row + col) % 2 == 0 ? "## " : "   ");
                } else {
                    System.out.print(piece + " ");
                }
            }
            System.out.println();
        }
        System.out.println("  A  B  C  D  E  F  G  H\n");
    }

    /**
     * Gets the internal grid representation of the board.
     *
     * @return 2D array of Pieces representing the board state
     */
    public Piece[][] getGrid() {
        return grid;
    }

    /**
     * Gets the current en passant target square, if any.
     *
     * @return the Position where en passant capture is possible, or null if none
     */
    public Position getEnPassantTarget() {
        return enPassantTarget;
    }

    /**
     * Places a piece at the specified position on the board.
     * Used primarily for pawn promotion.
     *
     * @param position the board position
     * @param piece the Piece to place, or null to clear the square
     */
    public void setPiece(Position position, Piece piece) {
        grid[position.row][position.col] = piece;
        if (piece != null) {
            piece.move(position);
        }
    }

    /**
     * Searches the board to find the position of the king of the specified color.
     *
     * @param color the color of the king to find ("white" or "black")
     * @return the Position of the king, or null if not found
     */
    public Position findKing(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return new Position(row, col);
                }
            }
        }
        return null;
    }

    /**
     * Determines if a specific position is under attack by pieces of the specified color.
     *
     * @param position the board position to check
     * @param byColor the color of the attacking pieces to check
     * @return true if the position is under attack, false otherwise
     */
    public boolean isPositionUnderAttack(Position position, String byColor) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null && piece.getColor().equals(byColor)) {
                    List<Position> moves = piece.possibleMoves(grid);
                    if (moves.contains(position)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the king of the specified color is currently in check.
     *
     * @param color the color of the king to check
     * @return true if the king is in check, false otherwise
     */
    public boolean isInCheck(String color) {
        Position kingPos = findKing(color);
        if (kingPos == null) return false;
        String opponentColor = color.equals("white") ? "black" : "white";
        return isPositionUnderAttack(kingPos, opponentColor);
    }

    /**
     * Simulates a move to determine if it would leave the player's king in check.
     * This is used to validate legal moves and prevent illegal moves that would
     * expose the king to attack.
     *
     * @param from the starting position of the piece
     * @param to the destination position
     * @param color the color of the player making the move
     * @return true if the move would leave the king in check, false otherwise
     */
    public boolean wouldMoveLeaveKingInCheck(Position from, Position to, String color) {
        // Simulate the move
        Piece piece = grid[from.row][from.col];
        Piece captured = grid[to.row][to.col];

        grid[to.row][to.col] = piece;
        grid[from.row][from.col] = null;

        boolean inCheck = isInCheck(color);

        // Undo the move
        grid[from.row][from.col] = piece;
        grid[to.row][to.col] = captured;

        return inCheck;
    }

    /**
     * Checks if a player has any legal moves remaining.
     * Used to detect checkmate (no legal moves while in check) and stalemate
     * (no legal moves while not in check).
     *
     * @param color the color of the player to check
     * @return true if the player has at least one legal move, false otherwise
     */
    public boolean hasAnyLegalMoves(String color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece != null && piece.getColor().equals(color)) {
                    List<Position> moves;
                    if (piece instanceof Pawn) {
                        moves = ((Pawn) piece).possibleMovesWithEnPassant(grid, enPassantTarget);
                    } else {
                        moves = piece.possibleMoves(grid);
                    }
                    for (Position move : moves) {
                        if (!wouldMoveLeaveKingInCheck(new Position(row, col), move, color)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
