import java.util.Scanner;

/**
 * Manages the chess game flow, including turn management, check/checkmate detection,
 * and overall game state. Orchestrates interaction between the Board and Players.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Game {
    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Player currentPlayer;
    private final Scanner scanner;

    /**
     * Constructs a new chess game with an initialized board and two players.
     * White player is set to move first.
     */
    public Game() {
        board = new Board();
        whitePlayer = new Player("white");
        blackPlayer = new Player("black");
        currentPlayer = whitePlayer;
        scanner = new Scanner(System.in);
    }

    /**
     * Main game loop that runs until checkmate or stalemate is reached.
     * Displays the board, checks for check/checkmate/stalemate conditions,
     * prompts the current player for a move, and switches turns.
     */
    public void play() {
        System.out.println("Welcome to Chess!");
        System.out.println("Enter moves in format: E2 E4\n");

        while (true) {
            board.display();

            // Check if current player is in check
            if (board.isInCheck(currentPlayer.getColor())) {
                System.out.println(currentPlayer.getColor() + " is in check!");
            }

            // Check for checkmate or stalemate
            if (!board.hasAnyLegalMoves(currentPlayer.getColor())) {
                if (board.isInCheck(currentPlayer.getColor())) {
                    String winner = (currentPlayer == whitePlayer) ? "Black" : "White";
                    System.out.println("Checkmate! " + winner + " wins!");
                } else {
                    System.out.println("Stalemate! It's a draw.");
                }
                scanner.close();
                return;
            }

            boolean moved = currentPlayer.makeMove(board, scanner);
            if (moved) {
                switchTurn();
            }
        }
    }

    /**
     * Switches the current player from white to black or vice versa.
     */
    private void switchTurn() {
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }
}
