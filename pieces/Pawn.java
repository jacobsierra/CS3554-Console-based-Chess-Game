package pieces;

import position.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Pawn chess piece.
 * Pawns move forward one square (or two on their first move),
 * capture diagonally, and can perform en passant captures.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Pawn extends Piece {
    /**
     * Constructs a Pawn with the specified color and position.
     *
     * @param color the color of the pawn ("white" or "black")
     * @param position the initial position of the pawn
     */
    public Pawn(String color, Position position) {
        super(color, position);
    }

    /**
     * Calculates possible moves without considering en passant.
     *
     * @param board the current board state
     * @return a list of possible destination positions
     */
    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        return possibleMovesWithEnPassant(board, null);
    }

    /**
     * Calculates all possible moves for this pawn, including en passant.
     * Handles forward movement (one or two squares from starting position),
     * diagonal captures, and en passant captures.
     *
     * @param board the current board state
     * @param enPassantTarget the position where en passant is possible, or null
     * @return a list of all possible destination positions
     */
    public List<Position> possibleMovesWithEnPassant(Piece[][] board, Position enPassantTarget) {
        List<Position> moves = new ArrayList<>();
        int direction = color.equals("white") ? -1 : 1;
        int row = position.row;
        int col = position.col;

        // One square forward
        int newRow = row + direction;
        if (newRow >= 0 && newRow < 8 && board[newRow][col] == null) {
            moves.add(new Position(newRow, col));

            // Two squares forward on first move
            int startRow = color.equals("white") ? 6 : 1;
            if (row == startRow) {
                int twoSquareRow = row + (2 * direction);
                if (board[twoSquareRow][col] == null) {
                    moves.add(new Position(twoSquareRow, col));
                }
            }
        }

        // Diagonal captures
        int[] captureCols = {col - 1, col + 1};
        for (int captureCol : captureCols) {
            if (captureCol >= 0 && captureCol < 8 && newRow >= 0 && newRow < 8) {
                Piece target = board[newRow][captureCol];
                if (target != null && !target.getColor().equals(color)) {
                    moves.add(new Position(newRow, captureCol));
                }
            }
        }

        // En passant
        if (enPassantTarget != null && newRow == enPassantTarget.row) {
            if (Math.abs(enPassantTarget.col - col) == 1) {
                moves.add(enPassantTarget);
            }
        }

        return moves;
    }

    /**
     * Returns the string representation of this pawn.
     *
     * @return "wp" for white pawn or "bp" for black pawn
     */
    @Override
    public String toString() {
        return color.equals("white") ? "wp" : "bp";
    }
}
