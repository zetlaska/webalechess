import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Chevron extends Piece {

    private final int imageNumber = 3;

    //Returns the index of the piece as an array
    public int getImageNumber() {
        return imageNumber;
    }

    //Returns the blue image for the piece
    public BufferedImage getRedImage() {
        return redImages[imageNumber];
    }

    //Returns the blue image for the piece
    public BufferedImage getBlueImage() {
        return blueImages[imageNumber];
    }

    //returns copy of Chevron
    public Piece clone() {
        return new Chevron(new Point(this.location.x, this.location.y), this.color, this.numMoves);
    }

    //creates a new Arrow
    public Chevron(Point location, Color color) {
        numMoves = 0;
        this.color = color;
        this.location = location;
    }

    //private constructor which will be used to make copies of Chevron
    private Chevron(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.location = location;
    }

    //Method to get all valid moves for Chevron
    public List<Movement> getValidMoves(Chessboard chessboard, boolean checkSun) {
        int x = location.x;
        int y = location.y;
        List<Movement> movements = new ArrayList<Movement>();

        if (chessboard == null)
            return movements;

        addIfValid(chessboard, movements, new Point(x + 1, y + 2));
        addIfValid(chessboard, movements, new Point(x - 1, y + 2));
        addIfValid(chessboard, movements, new Point(x + 1, y - 2));
        addIfValid(chessboard, movements, new Point(x - 1, y - 2));
        addIfValid(chessboard, movements, new Point(x + 2, y - 1));
        addIfValid(chessboard, movements, new Point(x + 2, y + 1));
        addIfValid(chessboard, movements, new Point(x - 2, y - 1));
        addIfValid(chessboard, movements, new Point(x - 2, y + 1));

        if (checkSun)
        for(int i = 0; i < movements.size(); i++)
            if (chessboard.movePutsSunInCheck(movements.get(i), this.color)) {
                movements.remove(movements.get(i));
                i--;
            }
        return movements;
    }

    //checks if a given move is valid from list of moves
    private void addIfValid(Chessboard chessboard, List<Movement> list, Point pt) {
        if(chessboard.validLocation(pt)) {
            Piece pc = chessboard.getPieceAt(pt);
            if(pc == null || pc.getColor() != this.color) {
                list.add(new Movement(this, pt, pc));
            }
        }
    }
}