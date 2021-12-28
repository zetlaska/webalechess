import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Triangle extends Piece {

    private final int imageNumber = 2;

    //Returns the index of the piece as an array
    public int getImageNumber() {
        return imageNumber;
    }

    //Returns the red image for the piece
    public BufferedImage getRedImage() {
        return redImages[imageNumber];
    }

    //Returns the blue image for the piece
    public BufferedImage getBlueImage() {
        return blueImages[imageNumber];
    }

    //returns copy of the Triangle
    public Piece clone() {
        return new Triangle(new Point(this.location.x, this.location.y),
                this.color, this.numMoves);
    }

    //creates a new Triangle
    public Triangle(Point location, Color color) {
        this.numMoves = 0;
        this.color = color;
        this.location = location;
    }

    //private constructor which will be used to make copies of Arrow
    private Triangle(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.location = location;
    }

    //Method to get all valid moves for Triangle
    public List<Movement> getValidMoves(Chessboard chessboard, boolean checkSun) {
        List<Movement> movements = new ArrayList<Movement>();

        if (chessboard == null)
            return movements;

        addMovesInLine(chessboard, movements, 1, 1);
        addMovesInLine(chessboard, movements, -1, 1);
        addMovesInLine(chessboard, movements, 1, -1);
        addMovesInLine(chessboard, movements, -1, -1);

        if (checkSun)
            for(int i = 0; i < movements.size(); i++)
                if (chessboard.movePutsSunInCheck(movements.get(i), this.color)) {
                    movements.remove(movements.get(i));
                    i--;
                }
        return movements;
    }

    //adds valid moves in a straight line to the list
    private void addMovesInLine(Chessboard chessboard, List<Movement> movements, int xi, int yi) {
        int x = location.x;
        int y = location.y;

        Point pt = new Point(x + xi, y + yi);
        Piece pc;

        while(chessboard.validLocation(pt)) {
            pc = chessboard.getPieceAt(pt);
            if(pc == null) {
                movements.add(new Movement(this, pt, pc));
            } else if(pc.getColor() != this.color) {
                movements.add(new Movement(this, pt, pc));
                break;
            } else {
                break;
            }
            pt = new Point(pt.x + xi, pt.y + yi);
        }
    }
}
