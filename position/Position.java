package position;

/**
 * Represents a position on the chess board using row and column indices.
 * Row 0 corresponds to rank 8 (black's back rank), and row 7 corresponds to rank 1 (white's back rank).
 * Column 0 corresponds to file A, and column 7 corresponds to file H.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Position {
    public int row;
    public int col;

    /**
     * Constructs a Position with the specified row and column.
     *
     * @param row the row index (0-7)
     * @param col the column index (0-7)
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Creates a Position from algebraic notation (e.g., "E2").
     * Note: This method is redundant with Utilities.notationToPosition().
     *
     * @param pos the position in algebraic notation (e.g., "A1", "H8")
     * @return a Position object corresponding to the notation
     */
    public static Position fromString(String pos) {
        int col = pos.charAt(0) - 'A';
        int row = 8 - Character.getNumericValue(pos.charAt(1));
        return new Position(row, col);
    }

    /**
     * Converts this Position to algebraic notation (e.g., "E2").
     *
     * @return the position in algebraic notation
     */
    public String toString() {
        return "" + (char) ('A' + col) + (8 - row);
    }

    /**
     * Compares this Position with another object for equality.
     *
     * @param obj the object to compare with
     * @return true if the positions have the same row and column, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return row == position.row && col == position.col;
    }

    /**
     * Generates a hash code for this Position based on its row and column.
     *
     * @return the hash code value for this Position
     */
    @Override
    public int hashCode() {
        return 31 * row + col;
    }
}
