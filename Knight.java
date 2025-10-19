import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Knight chess piece.
 * Knights move in an L-shape: two squares in one direction and one square perpendicular,
 * and can jump over other pieces.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Knight extends Piece {
    /**
     * Constructs a Knight with the specified color and position.
     *
     * @param color the color of the knight ("white" or "black")
     * @param position the initial position of the knight
     */
    public Knight(String color, Position position) {
        super(color, position);
    }

    /**
     * Calculates all possible L-shaped moves for this knight.
     * Knights can move to any of 8 possible positions (if within board bounds)
     * and can capture opponent pieces or move to empty squares.
     *
     * @param board the current board state
     * @return a list of all possible destination positions
     */
    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int[][] offsets = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        for (int[] offset : offsets) {
            int newRow = position.row + offset[0];
            int newCol = position.col + offset[1];
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Piece target = board[newRow][newCol];
                if (target == null || !target.getColor().equals(color)) {
                    moves.add(new Position(newRow, newCol));
                }
            }
        }
        return moves;
    }

    /**
     * Returns the string representation of this knight.
     *
     * @return "wN" for white knight or "bN" for black knight
     */
    @Override
    public String toString() {
        return color.equals("white") ? "wN" : "bN";
    }
}
