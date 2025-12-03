package gui;

import board.Board;
import pieces.*;
import position.Position;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.io.*;

/**
 * Main GUI class for the chess game using Swing.
 * Displays an 8x8 chessboard with pieces and handles user interaction.
 *
 * @author Jacob Sierra and Robert Zamora
 * @version 2.0
 */
public class ChessGUI extends JFrame {
    private Board board;
    private BoardPanel boardPanel;
    private String currentPlayer;
    private Position selectedPosition;
    private static final int SQUARE_SIZE = 80;
    private static final Color LIGHT_SQUARE = new Color(240, 217, 181);
    private static final Color DARK_SQUARE = new Color(181, 136, 99);
    private static final Color SELECTED_COLOR = new Color(186, 202, 68);
    private static final Color VALID_MOVE_COLOR = new Color(186, 202, 68, 100);

    /**
     * Constructs the chess GUI with initialized board and game state.
     */
    public ChessGUI() {
        board = new Board();
        currentPlayer = "white";
        selectedPosition = null;

        setTitle("Chess Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Create menu bar
        createMenuBar();

        boardPanel = new BoardPanel();
        add(boardPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Creates and sets up the menu bar with File menu (New Game, Save, Load, Exit).
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        // New Game menu item
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setMnemonic(KeyEvent.VK_N);
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newGameItem.addActionListener(e -> newGame());
        fileMenu.add(newGameItem);

        fileMenu.addSeparator();

        // Save Game menu item
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        saveGameItem.setMnemonic(KeyEvent.VK_S);
        saveGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        saveGameItem.addActionListener(e -> saveGame());
        fileMenu.add(saveGameItem);

        // Load Game menu item
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        loadGameItem.setMnemonic(KeyEvent.VK_L);
        loadGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        loadGameItem.addActionListener(e -> loadGame());
        fileMenu.add(loadGameItem);

        fileMenu.addSeparator();

        // Exit menu item
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Starts a new game by resetting the board and game state.
     */
    private void newGame() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Start a new game? Current game will be lost if not saved.",
            "New Game",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            board = new Board();
            currentPlayer = "white";
            selectedPosition = null;
            setTitle("Chess Game - White's turn");
            boardPanel.repaint();
        }
    }

    /**
     * Saves the current game state to a file using Java serialization.
     */
    private void saveGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game");
        fileChooser.setSelectedFile(new File("chess_game.sav"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                GameState gameState = new GameState(board, currentPlayer);
                oos.writeObject(gameState);
                JOptionPane.showMessageDialog(this,
                    "Game saved successfully!",
                    "Save Game",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error saving game: " + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Loads a previously saved game state from a file.
     */
    private void loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                GameState gameState = (GameState) ois.readObject();
                board = gameState.getBoard();
                currentPlayer = gameState.getCurrentPlayer();
                selectedPosition = null;
                setTitle("Chess Game - " + currentPlayer.substring(0, 1).toUpperCase() +
                         currentPlayer.substring(1) + "'s turn");
                boardPanel.repaint();
                JOptionPane.showMessageDialog(this,
                    "Game loaded successfully!",
                    "Load Game",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error loading game: " + ex.getMessage(),
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Custom JPanel that renders the chess board and handles mouse events.
     */
    private class BoardPanel extends JPanel {
        private Position draggedFrom;
        private Point dragPoint;

        public BoardPanel() {
            setPreferredSize(new Dimension(SQUARE_SIZE * 8, SQUARE_SIZE * 8));

            MouseAdapter mouseHandler = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    handleMousePressed(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    handleMouseReleased(e);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    handleMouseDragged(e);
                }
            };

            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
        }

        /**
         * Handles mouse press events for piece selection and drag start.
         */
        private void handleMousePressed(MouseEvent e) {
            Position pos = getPositionFromPoint(e.getPoint());
            if (pos == null) return;

            Piece piece = board.getPiece(pos);

            // Start dragging if there's a piece of the current player
            if (piece != null && piece.getColor().equals(currentPlayer)) {
                draggedFrom = pos;
                dragPoint = e.getPoint();
            }

            // Click to select
            if (selectedPosition == null && piece != null && piece.getColor().equals(currentPlayer)) {
                selectedPosition = pos;
                repaint();
            } else if (selectedPosition != null) {
                // Click to move
                attemptMove(selectedPosition, pos);
                selectedPosition = null;
                repaint();
            }
        }

        /**
         * Handles mouse release events for drag-and-drop completion.
         */
        private void handleMouseReleased(MouseEvent e) {
            if (draggedFrom != null) {
                Position draggedTo = getPositionFromPoint(e.getPoint());
                if (draggedTo != null) {
                    attemptMove(draggedFrom, draggedTo);
                }
                draggedFrom = null;
                dragPoint = null;
                selectedPosition = null;
                repaint();
            }
        }

        /**
         * Handles mouse drag events for visual piece dragging.
         */
        private void handleMouseDragged(MouseEvent e) {
            if (draggedFrom != null) {
                dragPoint = e.getPoint();
                repaint();
            }
        }

        /**
         * Converts screen coordinates to board position.
         */
        private Position getPositionFromPoint(Point point) {
            int col = point.x / SQUARE_SIZE;
            int row = point.y / SQUARE_SIZE;
            if (row >= 0 && row < 8 && col >= 0 && col < 8) {
                return new Position(row, col);
            }
            return null;
        }

        /**
         * Attempts to move a piece from one position to another.
         * Validates the move and updates game state.
         */
        private void attemptMove(Position from, Position to) {
            Piece piece = board.getPiece(from);
            if (piece == null || !piece.getColor().equals(currentPlayer)) {
                return;
            }

            // Get valid moves for the piece
            List<Position> validMoves;
            if (piece instanceof Pawn) {
                validMoves = ((Pawn) piece).possibleMovesWithEnPassant(
                    board.getGrid(), board.getEnPassantTarget());
            } else {
                validMoves = piece.possibleMoves(board.getGrid());
            }

            // Check if move is valid
            if (!validMoves.contains(to)) {
                JOptionPane.showMessageDialog(this, "Invalid move for that piece!");
                return;
            }

            // Check if move would leave king in check
            if (board.wouldMoveLeaveKingInCheck(from, to, currentPlayer)) {
                JOptionPane.showMessageDialog(this, "That move would leave your king in check!");
                return;
            }

            // Check if capturing the king (simplified win condition for Phase 2)
            Piece capturedPiece = board.getPiece(to);
            boolean kingCaptured = capturedPiece instanceof King;

            // Execute the move
            board.movePiece(from, to);

            // Handle pawn promotion
            if (piece instanceof Pawn) {
                int promotionRow = currentPlayer.equals("white") ? 0 : 7;
                if (to.row == promotionRow) {
                    handlePawnPromotion(to);
                }
            }

            // Check for king capture (win condition)
            if (kingCaptured) {
                String winner = currentPlayer.substring(0, 1).toUpperCase() + currentPlayer.substring(1);
                JOptionPane.showMessageDialog(this,
                    winner + " wins by capturing the King!",
                    "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            // Switch players
            currentPlayer = currentPlayer.equals("white") ? "black" : "white";
            setTitle("Chess Game - " + currentPlayer.substring(0, 1).toUpperCase() +
                     currentPlayer.substring(1) + "'s turn");

            repaint();
        }

        /**
         * Displays a dialog for pawn promotion and replaces the pawn.
         */
        private void handlePawnPromotion(Position position) {
            String[] options = {"Queen", "Rook", "Bishop", "Knight"};
            int choice = JOptionPane.showOptionDialog(
                this,
                "Promote pawn to:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            Piece promotedPiece;
            switch (choice) {
                case 1:
                    promotedPiece = new Rook(currentPlayer, position);
                    break;
                case 2:
                    promotedPiece = new Bishop(currentPlayer, position);
                    break;
                case 3:
                    promotedPiece = new Knight(currentPlayer, position);
                    break;
                default:
                    promotedPiece = new Queen(currentPlayer, position);
                    break;
            }
            board.setPiece(position, promotedPiece);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw board squares
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    drawSquare(g2d, row, col);
                }
            }

            // Draw pieces (except dragged piece)
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Position pos = new Position(row, col);
                    if (draggedFrom == null || !pos.equals(draggedFrom)) {
                        drawPiece(g2d, row, col);
                    }
                }
            }

            // Draw dragged piece at cursor
            if (draggedFrom != null && dragPoint != null) {
                Piece draggedPiece = board.getPiece(draggedFrom);
                if (draggedPiece != null) {
                    String symbol = getPieceSymbol(draggedPiece);
                    g2d.setFont(new Font("Arial Unicode MS", Font.PLAIN, 60));
                    FontMetrics fm = g2d.getFontMetrics();
                    g2d.setColor(draggedPiece.getColor().equals("white") ? Color.WHITE : Color.BLACK);
                    g2d.drawString(symbol,
                        dragPoint.x - fm.stringWidth(symbol) / 2,
                        dragPoint.y + fm.getAscent() / 2);
                }
            }
        }

        /**
         * Draws a single square on the board with appropriate coloring.
         */
        private void drawSquare(Graphics2D g2d, int row, int col) {
            Position pos = new Position(row, col);

            // Determine square color
            Color squareColor = (row + col) % 2 == 0 ? LIGHT_SQUARE : DARK_SQUARE;

            // Highlight selected square
            if (selectedPosition != null && selectedPosition.equals(pos)) {
                squareColor = SELECTED_COLOR;
            }

            g2d.setColor(squareColor);
            g2d.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);

            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.drawRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        }

        /**
         * Draws a chess piece at the specified position.
         */
        private void drawPiece(Graphics2D g2d, int row, int col) {
            Piece piece = board.getPiece(new Position(row, col));
            if (piece == null) return;

            String symbol = getPieceSymbol(piece);
            g2d.setFont(new Font("Arial Unicode MS", Font.PLAIN, 60));
            FontMetrics fm = g2d.getFontMetrics();

            int x = col * SQUARE_SIZE + (SQUARE_SIZE - fm.stringWidth(symbol)) / 2;
            int y = row * SQUARE_SIZE + (SQUARE_SIZE + fm.getAscent()) / 2 - 5;

            // Draw piece with shadow for better visibility
            g2d.setColor(piece.getColor().equals("white") ? Color.WHITE : Color.BLACK);
            g2d.drawString(symbol, x, y);
        }

        /**
         * Returns the Unicode symbol for a chess piece.
         */
        private String getPieceSymbol(Piece piece) {
            boolean isWhite = piece.getColor().equals("white");

            if (piece instanceof King) return isWhite ? "♔" : "♚";
            if (piece instanceof Queen) return isWhite ? "♕" : "♛";
            if (piece instanceof Rook) return isWhite ? "♖" : "♜";
            if (piece instanceof Bishop) return isWhite ? "♗" : "♝";
            if (piece instanceof Knight) return isWhite ? "♘" : "♞";
            if (piece instanceof Pawn) return isWhite ? "♙" : "♟";

            return "";
        }
    }

    /**
     * Main method to launch the chess GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessGUI());
    }
}
