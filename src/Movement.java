import java.awt.*;

public class Movement {
    private Piece toMove;
    private Point moveTo;
    private Piece toCapture;

    //creates a new move object
    public Movement(Piece toMove, Point moveTo, Piece toCapture)
    {
        this.toMove = toMove;
        this.moveTo = moveTo;
        this.toCapture = toCapture;
    }

    //returns destination of the move
    public Point getMoveTo() {return moveTo;}
    //returns the piece being moved
    public Piece getPiece() {return toMove;}
    //returns the piece being captured
    public Piece getCaptured() {return toCapture;}

}
