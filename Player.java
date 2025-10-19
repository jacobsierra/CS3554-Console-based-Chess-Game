import java.util.Scanner;
import java.util.List;

/**
 * Represents a chess player. Handles move input, validation, and pawn promotion.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Player {
    private final String color;

    /**
     * Constructs a new player with the specified color.
     *
     * @param color the color of this player ("white" or "black")
     */
    public Player(String color) {
        this.color = color;
    }

    /**
     * Gets the color of this player.
     *
     * @return the player's color ("white" or "black")
     */
    public String getColor() {
        return color;
    }

    /**
     * Prompts the player to make a move and validates it. Handles:
     * - Move input parsing (algebraic notation)
     * - Piece ownership verification
     * - Move legality validation
     * - Check prevention
     * - Pawn promotion when a pawn reaches the opposite end
     *
     * @param board the game board
     * @param scanner Scanner for reading player input
     * @return true if a valid move was made, false if the input was invalid
     */
    public boolean makeMove(Board board, Scanner scanner) {
        System.out.print(color.substring(0, 1).toUpperCase() + color.substring(1) + " to move: ");
        String input = scanner.nextLine().trim().toUpperCase();
        String[] parts = input.split(" ");
        if (parts.length != 2 || !Utilities.isValidNotation(parts[0]) || !Utilities.isValidNotation(parts[1])) {
            System.out.println("Invalid input format. Please use the format: E2 E4.");
            return false;
        }
        Position from = Utilities.notationToPosition(parts[0]);
        Position to = Utilities.notationToPosition(parts[1]);
        Piece piece = board.getPiece(from);
        if (piece == null) {
            System.out.println("No piece at " + parts[0]);
            return false;
        }
        if (!piece.getColor().equals(this.color)) {
            System.out.println("You can only move your own pieces.");
            return false;
        }

        List<Position> validMoves;
        if (piece instanceof Pawn) {
            validMoves = ((Pawn) piece).possibleMovesWithEnPassant(board.getGrid(), board.getEnPassantTarget());
        } else {
            validMoves = piece.possibleMoves(board.getGrid());
        }

        if (!validMoves.contains(to)) {
             System.out.println("Invalid move for that piece.");
             return false;
        }

        // Check if move would leave king in check
        if (board.wouldMoveLeaveKingInCheck(from, to, color)) {
            System.out.println("That move would leave your king in check.");
            return false;
        }

        board.movePiece(from, to);

        // Handle pawn promotion
        if (piece instanceof Pawn) {
            int promotionRow = color.equals("white") ? 0 : 7;
            if (to.row == promotionRow) {
                System.out.println("Pawn promotion! Choose piece (Q/R/B/N): ");
                String choice = scanner.nextLine().trim().toUpperCase();
                Piece promotedPiece;
                switch (choice) {
                    case "R":
                        promotedPiece = new Rook(color, to);
                        break;
                    case "B":
                        promotedPiece = new Bishop(color, to);
                        break;
                    case "N":
                        promotedPiece = new Knight(color, to);
                        break;
                    case "Q":
                    default:
                        promotedPiece = new Queen(color, to);
                        break;
                }
                board.setPiece(to, promotedPiece);
            }
        }

        return true;
    }
}
