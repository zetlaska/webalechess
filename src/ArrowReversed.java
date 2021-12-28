import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ArrowReversed extends Piece {

    private final int imageNumber = 5;

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

    //returns copy of Arrow
    public Piece clone() {
        return new ArrowReversed(new Point(this.location.x, this.location.y), this.color, this.numMoves);
    }

    //creates a new Arrow
    public ArrowReversed(Point location, Color color) {
        this.numMoves = 0;
        this.color = color;
        this.location = location;

    }

    //private constructor which will be used to make copies of Arrow
    private ArrowReversed(Point location, Color color, int moves) {
        this.numMoves = moves;
        this.color = color;
        this.location = location;
    }

    //Method to get all valid moves for Arrow
    public List<Movement> getValidMoves(Chessboard chessboard, boolean checkSun) {
        List<Movement> movements = new ArrayList<Movement>();

        if (chessboard == null)
            return movements;
        advance(chessboard, movements);
        capture(chessboard, movements);
        if (checkSun)
            for (int i = 0; i < movements.size(); i++)
                if (chessboard.movePutsSunInCheck(movements.get(i), this.color)) {
                    movements.remove(movements.get(i));
                    i--;
                }
        return movements;
    }

    //add moves for when the Arrow advances to list of moves
    private void advance(Chessboard chessboard, List<Movement> movements) {
        int x = location.x;
        int y = location.y;

        Piece pc;
        Point pt;
        int move;

        if (color == Color.Red)
            move = 1;
        else
            move = -1;

        pt = new Point(x, y+move);
        if(chessboard.validLocation(pt)) {
            pc = chessboard.getPieceAt(pt);
            if (pc == null) {
                movements.add(new Movement(this, pt, pc));
                pt = new Point(x, y+move *2);
                if (chessboard.validLocation(pt)) {
                    pc = chessboard.getPieceAt(pt);
                    if(pc == null && numMoves == 0)
                        movements.add(new Movement(this,pt,pc));
                }
            }
        }
    }

    //add capture moves for when the Arrow captures to list of moves
    private void capture(Chessboard chessboard, List<Movement> movements) {
        int x = location.x;
        int y = location.y;
        Piece pc;
        Point pt;
        int move;

        if(color == Color.Red)
            move = 1;
        else
            move = -1;

        pt = new Point(x, y+move);
        if(chessboard.validLocation(pt)) {
            pc = chessboard.getPieceAt(pt);
            if (pc != null)
                if(this.color !=pc.getColor())
                    movements.add(new Movement(this, pt,pc));
        }
        pt = new Point(x, y+move);
        if(chessboard.validLocation(pt)) {
            pc = chessboard.getPieceAt(pt);
            if (pc != null)
                if(this.color !=pc.getColor())
                    movements.add(new Movement(this, pt,pc));
        }
    }
}
