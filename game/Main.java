package game;

/**
 * Main entry point for the Console-based Chess Game.
 * This class initializes and starts a new chess game.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 1.0
 */
public class Main {
    /**
     * Main method that creates and starts a chess game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
