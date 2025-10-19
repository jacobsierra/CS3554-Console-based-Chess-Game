import java.util.ArrayList;
import java.util.List;

/**
 * Represents a King chess piece.
 * Kings move one square in any direction (horizontally, vertically, or diagonally).
 * The King can also perform castling with a rook if neither piece has moved.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class King extends Piece {
    /**
     * Constructs a King with the specified color and position.
     *
     * @param color the color of the king ("white" or "black")
     * @param position the initial position of the king
     */
    public King(String color, Position position) {
        super(color, position);
    }

    /**
     * Calculates all possible moves for this king, including castling.
     * Normal moves: one square in any of 8 directions.
     * Castling: if the king hasn't moved, can castle kingside or queenside
     * with an unmoved rook if squares between them are empty.
     *
     * @param board the current board state
     * @return a list of all possible destination positions
     */
    @Override
    public List<Position> possibleMoves(Piece[][] board) {
        List<Position> moves = new ArrayList<>();
        int[][] offsets = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1},  {1, 0},  {1, 1}
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

        // Castling
        if (!hasMoved) {
            int row = position.row;
            // Kingside castling
            Piece kingsideRook = board[row][7];
            if (kingsideRook instanceof Rook && !kingsideRook.hasMoved() &&
                kingsideRook.getColor().equals(color)) {
                if (board[row][5] == null && board[row][6] == null) {
                    moves.add(new Position(row, 6));
                }
            }
            // Queenside castling
            Piece queensideRook = board[row][0];
            if (queensideRook instanceof Rook && !queensideRook.hasMoved() &&
                queensideRook.getColor().equals(color)) {
                if (board[row][1] == null && board[row][2] == null && board[row][3] == null) {
                    moves.add(new Position(row, 2));
                }
            }
        }

        return moves;
    }

    /**
     * Returns the string representation of this king.
     *
     * @return "wK" for white king or "bK" for black king
     */
    @Override
    public String toString() {
        return color.equals("white") ? "wK" : "bK";
    }
}
