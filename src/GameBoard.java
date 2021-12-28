import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class GameBoard extends JComponent implements MouseListener {

    private enum GameStatus {Idle, Started, Checkmate, Draw, Error}
    GameStatus status = GameStatus.Idle;
    boolean imagesLoaded = false;
    Chessboard gameChessboard;
    //piece selected by user
    Piece selectedPiece = null;
    //invalid piece selected by user
    Piece invalidPiece = null;
    //list of possible moves for selected piece
    List<Movement> legalMovements;

    // defines colors for different elements of the game
    final Color invalidColor = new Color(255, 0, 0, 127);
    final Color selectionColor = new Color(255, 255, 0, 127);
    final Color validMoveColor = new Color(0, 255, 0, 127);
    final Color checkColor = new Color(127, 0, 255, 127);
    final Color lastMovedColor = new Color(0, 255, 255, 75);
    final Color lightColor = new Color(255, 255, 255, 255);
    final Color darkColor = new Color(184, 182, 182, 255);

    //creates a new new GamePanel component
    public GameBoard(int w, int h) {
        // sets size
        this.setSize(w, h);
        // loads piece image from file
        loadImages();
        // initialize the game
        newGame();
        // adds a MouseListener
        this.addMouseListener(this);
    }

    public void newGame() {
        //creates a new board
        gameChessboard = new Chessboard(true);
        status = GameStatus.Started;
        //resets variable
        selectedPiece = null;
        invalidPiece = null;
        //draws the new board
        this.repaint();
    }

    //load the state of the board from a saveGame
    public void loadBoard() {
        selectedPiece = null;
        invalidPiece = null;

        try {
            File directory = new File("SaveGame");

            if (!directory.exists())
                directory.mkdir();
            File[] saves = directory.listFiles();

            if (saves.length == 0) {
                // inform the user
                JOptionPane.showMessageDialog(this,
                        "No saved game detected",
                        "Load game",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Object response = JOptionPane.showInputDialog(this, "Select save file:",
                    "Load Game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    saves,
                    saves[0]);

            if (response == null)
                return;

            FileInputStream fis = new FileInputStream((File) response);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.gameChessboard = (Chessboard) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            String message = "Could not load save game."+ e.getMessage();
            JOptionPane.showMessageDialog(this,
                    message,
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        if (gameChessboard.gameOver()) {
            if (gameChessboard.getPieceInCheck() == null)
                status = GameStatus.Draw;
            else
                status = GameStatus.Checkmate;
        }
        this.repaint();
    }

    //save the state of the board to a file
    public void saveBoard() {
        try {
            String name = JOptionPane.showInputDialog("Save file name: ");
            if (name == null)
                return;
            File directory = new File("SaveGame");
            if (!directory.exists())
                directory.mkdir();
            FileOutputStream fos = new FileOutputStream(new File("SaveGame/" + name + ".CSV"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.gameChessboard);
            oos.close();
            fos.close();
        } catch (Exception e) {
            String message = e.getMessage();
            JOptionPane.showMessageDialog(this,
                    message, "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    //returns the board to its previous
    public void undo() {
        selectedPiece = null;
        invalidPiece = null;
        legalMovements = null;

        gameChessboard = gameChessboard.getPreviousState();
        status = GameStatus.Started;

        this.repaint();
    }

    //loads piece images from a specified folder
    private void loadImages() {
        try {
            BufferedImage[] redImages = new BufferedImage[6];
            BufferedImage[] blueImages = new BufferedImage[6];
            File directory = new File("Assets");
            if (!directory.exists()) {
                if (directory.mkdir()) {
                    throw new Exception("Images could not be loaded, please copy images into Assets directory");
                }
            }
            redImages[0] = ImageIO.read(new File("ASSETS/RED_ARROW.PNG"));
            redImages[1] = ImageIO.read(new File("ASSETS/RED_PLUS.PNG"));
            redImages[2] = ImageIO.read(new File("ASSETS/RED_TRIANGLE.PNG"));
            redImages[3] = ImageIO.read(new File("ASSETS/RED_CHEVRON.PNG"));
            redImages[4] = ImageIO.read(new File("ASSETS/RED_SUN.PNG"));
            redImages[5] = ImageIO.read(new File("ASSETS/RED_ARROW_REVERSED.PNG"));

            blueImages[0] = ImageIO.read(new File("ASSETS/BLUE_ARROW.PNG"));
            blueImages[1] = ImageIO.read(new File("ASSETS/BLUE_PLUS.PNG"));
            blueImages[2] = ImageIO.read(new File("ASSETS/BLUE_TRIANGLE.PNG"));
            blueImages[3] = ImageIO.read(new File("ASSETS/BLUE_CHEVRON.PNG"));
            blueImages[4] = ImageIO.read(new File("ASSETS/BLUE_SUN.PNG"));
            blueImages[5] = ImageIO.read(new File("ASSETS/BLUE_ARROW_REVERSED.PNG"));


            Piece.setRedImages(blueImages);
            Piece.setBlueImages(redImages);

            imagesLoaded = true;
        } catch (Exception e) {
            status = GameStatus.Error;
            String message = "Could not load images. " + e.getMessage();
            JOptionPane.showMessageDialog(this, message, "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //responds to a mousePressed event
    public void mousePressed(MouseEvent e) {
        status = GameStatus.Started;
        if (status == GameStatus.Started) {
            invalidPiece = null;
            int w = getWidth();
            int h = getHeight();
            Point boardPt = new Point(e.getPoint().x / (w / 7), e.getPoint().y / (h / 8));

            if (selectedPiece == null) {
                selectedPiece = gameChessboard.getPieceAt(boardPt);
                if (selectedPiece != null) {
                    legalMovements = selectedPiece.getValidMoves(gameChessboard, true);
                    if (selectedPiece.getColor() != gameChessboard.getTurn()) {
                        legalMovements = null;
                        invalidPiece = selectedPiece;
                        selectedPiece = null;
                    }
                }
            } else {
                Movement playerMovement = moveWithDestination(boardPt);
                if (playerMovement != null) {
                    gameChessboard.doMove(playerMovement, true);
                    selectedPiece = null;
                    legalMovements = null;
                } else {
                    selectedPiece = null;
                    legalMovements = null;
                }
            }

        }

            if (gameChessboard.gameOver()) {
                this.paintImmediately(0, 0, this.getWidth(), this.getHeight());

                if (gameChessboard.getPieceInCheck() == null) {
                    status = GameStatus.Draw;
                    JOptionPane.showMessageDialog(this,
                            "Draw!",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    status = GameStatus.Checkmate;
                    JOptionPane.showMessageDialog(this,
                            "Checkmate!",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            this.repaint();
        }


        //overrides the default paintComponent method
    @Override
    protected void paintComponent(Graphics gr) {
        int w = getWidth();
        int h = getHeight();

        int sW = w / 7;
        int sH = h / 8;

        Image buffer = createImage(w, h);
        Graphics g = buffer.getGraphics();
        drawBoard(g, sW, sH);
        drawHighlightCircles(g, sW, sH);

        if (imagesLoaded)
            drawPieces(g, sW, sH);

        gr.drawImage(buffer, 0, 0, this);
    }


    //draw a circle to to highlight and show valid piece, invalid piece, valid moves, invalid moves, checks, etc
    private void drawHighlightCircles(Graphics g, int sW, int sH) {

        if(selectedPiece != null) {
            Point p = selectedPiece.getLocation();
            g.setColor(selectionColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);

            g.setColor(validMoveColor);
            for(Movement m : legalMovements) {
                Point pt = m.getMoveTo();
                g.fillOval(pt.x * sW, pt.y * sH, sW, sH);
            }
        }
        if(invalidPiece != null) {
            Point p = invalidPiece.getLocation();
            g.setColor(invalidColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
        }
        if (gameChessboard.getPieceInCheck() != null) {
            Point p = gameChessboard.getPieceInCheck().getLocation();
            g.setColor(checkColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
        }
        if (gameChessboard.getLastMovedPiece() != null) {
            Point p = gameChessboard.getLastMovedPiece().getLocation();
            g.setColor(lastMovedColor);
            g.fillOval(p.x * sW, p.y * sH, sW, sH);
        }
    }

    //draws pieces to the graphics object
    private void drawPieces(Graphics g, int sW, int sH) {
        for(Piece pc : gameChessboard.getPieces()) {
            if(pc.getColor() == Piece.Color.Blue) {
                g.drawImage(pc.getRedImage(), pc.getLocation().x * sW,
                        pc.getLocation().y * sH, sW, sH, null);
            } else {
                g.drawImage(pc.getBlueImage(), pc.getLocation().x * sW,
                        pc.getLocation().y * sH, sW, sH, null);
            }
        }
    }

    //draws an empty board
    private void drawBoard(Graphics g, int sW, int sH) {
        g.setColor(lightColor);
        g.fillRect(0, 0, sW * 7, sH * 8);

        boolean dark = false;
        g.setColor(darkColor);
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                if(dark) {
                    g.fillRect(x * sW, y * sH, sW, sH);
                }
                dark = !dark;
            }
            dark = !dark;
        }
    }

    //gets the move with a given point as its destination from the list of moves
    private Movement moveWithDestination(Point  pt) {
        for(Movement m : legalMovements)
            if(m.getMoveTo().equals(pt))
                return m;
        return null;
    }

    public void mouseExited(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}





