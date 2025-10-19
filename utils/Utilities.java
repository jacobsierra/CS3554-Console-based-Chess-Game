package utils;

import position.Position;

/**
 * Utility class providing helper methods for chess notation conversion and validation.
 * Handles conversion between algebraic notation (e.g., "E2") and internal Position objects.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Utilities {
    /**
     * Converts algebraic notation to a Position object.
     * For example, "E2" is converted to row 6, column 4.
     *
     * @param notation the algebraic notation (e.g., "A1", "H8")
     * @return a Position object, or null if the notation is invalid
     */
    public static Position notationToPosition(String notation) {
        if (!isValidNotation(notation)) return null;
        char file = Character.toUpperCase(notation.charAt(0));
        int rank = Character.getNumericValue(notation.charAt(1));
        int col = file - 'A';
        int row = 8 - rank;
        return new Position(row, col);
    }

    /**
     * Converts a Position object to algebraic notation.
     * For example, Position(6, 4) is converted to "E2".
     *
     * @param position the Position to convert
     * @return the algebraic notation string
     */
    public static String positionToNotation(Position position) {
        char file = (char) ('A' + position.col);
        int rank = 8 - position.row;
        return "" + file + rank;
    }

    /**
     * Validates whether a string is valid algebraic notation.
     * Valid notation consists of a file (A-H) followed by a rank (1-8).
     *
     * @param notation the string to validate
     * @return true if the notation is valid, false otherwise
     */
    public static boolean isValidNotation(String notation) {
        if (notation == null || notation.length() != 2) return false;
        char file = Character.toUpperCase(notation.charAt(0));
        char rank = notation.charAt(1);
        return file >= 'A' && file <= 'H' && rank >= '1' && rank <= '8';
    }
}
