package pieces;

import position.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Queen chess piece.
 * Queens combine the movement of rooks and bishops, moving any number of squares
 * horizontally, vertically, or diagonally until blocked by another piece or the board edge.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Queen extends Piece {
    /**
     * Constructs a Queen with the specified color and position.
     *
     * @param color the color of the queen ("white" or "black")
     * @param position the initial position of the queen
     */
    public Queen(String color, Position position) {
        super(color, position);
    }

    /**
     * Calculates all possible moves for this queen.
     * Combines rook-like (horizontal/vertical) and bishop-like (diagonal) movement.
     * Moves along eight directions until hitting another piece or the board edge.
     * Can capture opponent pieces but cannot move through pieces.
     *
     * @param board the current board state
     * @return a list of all possible destination positions
     */
    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int[][] directions = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
        };
        for (int[] dir : directions) {
            int row = position.row;
            int col = position.col;
            while (true) {
                row += dir[0];
                col += dir[1];
                if (row < 0 || row >= 8 || col < 0 || col >= 8) {
                    break;
                }
                if (board[row][col] == null) {
                    moves.add(new Position(row, col));
                } 
                else {
                    if (!board[row][col].getColor().equals(color)) {
                        moves.add(new Position(row, col));
                    }
                    break;
                }
            }
        }
        return moves;
    }

    /**
     * Returns the string representation of this queen.
     *
     * @return "wQ" for white queen or "bQ" for black queen
     */
    @Override
    public String toString() {
        return color.equals("white") ? "wQ" : "bQ";
    }
}
