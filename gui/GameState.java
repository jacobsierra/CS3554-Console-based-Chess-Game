package gui;

import board.Board;
import java.io.Serializable;

/**
 * Represents a serializable snapshot of the chess game state.
 * Used for saving and loading games.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 2.0
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Board board;
    private final String currentPlayer;

    /**
     * Constructs a GameState with the current board and player.
     *
     * @param board the current board state
     * @param currentPlayer the color of the current player ("white" or "black")
     */
    public GameState(Board board, String currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Gets the board from this game state.
     *
     * @return the Board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the current player from this game state.
     *
     * @return the current player's color
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
