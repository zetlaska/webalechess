import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Sun extends Piece {
    private final int imageNumber = 4;

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

    //returns copy of the Sun
    public Piece clone() {
        return new Sun(new Point(this.location.x, this.location.y), this.color, this.numMoves);
    }

    //creates a new Sun
    public Sun(Point location, Color color) {
        numMoves = 0;
        this.color = color;
        this.location = location;
    }

    //private constructor which will be used to make copies of Sun
    private Sun(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.location = location;
    }

    //Method to get all valid moves for Sun
    public List<Movement> getValidMoves(Chessboard chessboard, boolean checkSun) {
        int x = location.x;
        int y = location.y;

        List<Movement> movements = new ArrayList<Movement>();

        //if no board, will return an empty list
        if (chessboard == null)
            return movements;

        addIfValid(chessboard, movements, new Point(x - 1, y - 1));
        addIfValid(chessboard, movements, new Point(x, y - 1));
        addIfValid(chessboard, movements, new Point(x + 1, y - 1));
        addIfValid(chessboard, movements, new Point(x + 1, y));
        addIfValid(chessboard, movements, new Point(x + 1, y + 1));
        addIfValid(chessboard, movements, new Point(x, y + 1));
        addIfValid(chessboard, movements, new Point(x - 1, y + 1));
        addIfValid(chessboard, movements, new Point(x - 1, y));

        // check move doesn't the Sun doesn't put itself in check
        if(checkSun)
            for(int i = 0; i < movements.size(); i++)
                if (chessboard.movePutsSunInCheck(movements.get(i), this.color)) {
                    movements.remove((movements.get(i)));
                    i--;
                }
        return movements;
    }

    //checks if a give move is valid
    private void addIfValid(Chessboard chessboard, List<Movement> list, Point pt) {
        // if the location is valid
        if(chessboard.validLocation(pt)) {
            // and the location does not contain same color piece
            Piece pc = chessboard.getPieceAt(pt);
            if(pc == null || pc.getColor() != this.color) {
                // all the move to the list
                list.add(new Movement(this, pt, pc));
            }
        }
    }
}
