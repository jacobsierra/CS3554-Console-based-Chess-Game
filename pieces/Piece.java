package pieces;

import position.Position;
import java.util.List;
import java.io.Serializable;

/**
 * Abstract base class representing a chess piece.
 * All specific piece types (Pawn, Knight, Bishop, Rook, Queen, King) extend this class.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public abstract class Piece implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String color;
    protected Position position;
    protected boolean hasMoved;

    /**
     * Constructs a Piece with the specified color and position.
     *
     * @param color the color of the piece ("white" or "black")
     * @param position the initial position of the piece
     */
    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
        this.hasMoved = false;
    }

    /**
     * Gets the color of this piece.
     *
     * @return the piece's color ("white" or "black")
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the current position of this piece.
     *
     * @return the piece's position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Moves this piece to a new position and marks it as having moved.
     * This method is called by the Board after a move is validated.
     *
     * @param newPosition the new position for this piece
     */
    public void move(Position newPosition) {
        this.position = newPosition;
        this.hasMoved = true;
    }

    /**
     * Checks if this piece has been moved from its starting position.
     * Used for castling and pawn two-square move validation.
     *
     * @return true if the piece has moved, false otherwise
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Calculates all possible moves for this piece given the current board state.
     * This method must be implemented by each specific piece type.
     *
     * @param board the current board state as a 2D array
     * @return a list of all possible destination positions
     */
    public abstract List<Position> possibleMoves(Piece[][] board);

    /**
     * Returns a string representation of this piece for display purposes.
     *
     * @return a 2-3 character string representing the piece (e.g., "wp", "bK")
     */
    public abstract String toString();
}
