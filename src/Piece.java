import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public abstract class Piece implements Serializable, Cloneable {
    public static enum Color {Red, Blue};
    protected static BufferedImage[] redImages;
    protected static BufferedImage[] blueImages;
    protected int numMoves;
    protected Color color;
    protected Point location;

    //returns the number of moves made by the piece
    public int getNumberOfMoves() {return numMoves;}
    //gets the color of the piece
    public Color getColor() {return this.color;}

    //moves the piece to a new location
    public void moveTo(Point p) {
        this.location = p;
        numMoves++;
    }

    //return the location of the piece
    public Point getLocation() {return this.location;}
    //returns the index of the Piece's image in an array
    public abstract int getImageNumber();
    //gets the red image of the piece
    public abstract BufferedImage getRedImage();
    //gets the blue image of the piece
    public abstract BufferedImage getBlueImage();
    //gets the list of valid moves a piece can make
    public abstract List<Movement> getValidMoves(Chessboard chessboard, boolean checkSun);
    //sets an array of Buffered images for Red pieces
    public static void setRedImages(BufferedImage[] images) {redImages = images;}
    //sets an array of Buffered images for Blue pieces
    public static void setBlueImages(BufferedImage[] images) {blueImages = images;}

    //returns a copy of the piece
    @Override
    public abstract Piece clone();
}
